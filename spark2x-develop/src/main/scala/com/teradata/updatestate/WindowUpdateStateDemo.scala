package com.teradata.updatestate

import org.apache.spark.{SparkConf, SparkContext}
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
object WindowUpdateStateDemo {

    def main(args: Array[String]): Unit = {
      val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")

      //时间片是2秒
      val ssc = new StreamingContext(conf ,Seconds(2))

      ssc.checkpoint("file:///d:/java/chk")

      //创建套接字文本流
      val lines = ssc.socketTextStream("192.168.20.203", 9999)

      //压扁生成单词流
      val words = lines.flatMap(_.split(" "))

      //标一成对
      val pairs = words.map((_,1))

      //
      val result =  pairs.reduceByKeyAndWindow((a: Int, b: Int) => a + b, Seconds(2), Seconds(4))
//      val result = pairs.reduceByKey((a:Int,b:Int)=>a + b)

      //状态更新函数
      def updateFunc(newValue:Seq[Int],oldState:Option[List[Tuple2[Long,Int]]]):Option[List[Tuple2[Long,Int]]] = {
        //取出当前毫秒时间
        val currentMS = System.currentTimeMillis()
        var count =0;
        for(e <- newValue){
          count = count+e
        }
        if (oldState.isEmpty){
          Some(List((currentMS,count)))
        }else{
          //处理之前旧数据
          var newList:List[Tuple2[Long,Int]]=Nil
          for(t<-oldState.get){
            val ms0=t._1
            if ((currentMS-10000)<ms0){
              newList = newList.:+(t)
            }
          }
          newList = newList.:+ ((currentMS,count))
          Some(newList)
        }
      }
      result.updateStateByKey(updateFunc).print()
      //启动流
      ssc.start()

      ssc.awaitTermination()
    }

}
