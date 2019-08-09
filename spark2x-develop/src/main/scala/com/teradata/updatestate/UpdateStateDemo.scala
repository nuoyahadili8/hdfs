package com.teradata.updatestate

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @Project:
  * @Description:
  * @Version 1.0.0
  * @Throws SystemException:
  * @Author: <li>2019/8/9/009 Administrator Create 1.0
  * @Copyright ©2018-2019 al.github
  * @Modified By:
  */
object UpdateStateDemo {

  def main(args: Array[String]): Unit = {

    //开本地线程两个处理
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    //每隔1秒计算一批数据
    val ssc = new StreamingContext(conf, Seconds(2))

    ssc.checkpoint("file:///d:/java/chk")

    //监控机器ip为192.168.1.187:9999端号的数据,注意必须是这个9999端号服务先启动nc -l 9999，否则会报错,但进程不会中断
    val lines = ssc.socketTextStream("192.168.20.203", 9999)
    //按空格切分输入数据
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map((_,1))
    val result = pairs.reduceByKey((a:Int,b:Int)=>a + b)

    //状态更新函数
    def updateFunc(a:Seq[Int],b:Option[Int]):Option[Int] = {
      var count:Int = 0 ;
      for(e <- a){
        count = count + e
      }

      var newcount:Int = 0 ;
      if(b.isEmpty){
        newcount = count ;
      }
      else{
        newcount = count + b.get;
      }

      if(newcount >= 5){
        None
      }
      else{
        Some(newcount)
      }
    }

    result.updateStateByKey(updateFunc).print()
    //启动流
    ssc.start()

    ssc.awaitTermination()
  }

}
