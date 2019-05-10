package com.teradata.spark2x.struct

import java.sql.Timestamp

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.TimestampType
/**
  * @Project:
  * @Description:
  * @Version 1.0.0
  * @Throws SystemException:
  * @Author: <li>2018/12/7/007 Administrator Create 1.0
  * @Copyright ©2018-2019 al.github
  * @Modified By:
  */
object StructuredStramingDemo1 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("StructuredKafkaAgg").master("local[2]").getOrCreate()
    // 从 socket 读取 text
    val lines = spark.readStream.format("socket").option("host", "localhost").option("port", 5000).load()

    import spark.implicits._
    // 将 lines 切分为 words
    val words = lines.as[String].map(_.split(","))
    val formatDf = words.map(x => MonitorEntity(x(0), x(1), x(2)))

    //    val formatDf1 = words.map(x => (x(0), x(1), x(2), x(3).toLong * 1000, x(4), x(5).toDouble))
    //
    //    val formatDf = formatDf1.select($"metric_name",$"module",$"classify",$"timestamp".cast(TimestampType),$"host_name",$"value")


    // 生成正在运行的 word count
    val wordCounts = formatDf.withWatermark("timestamp", "10 seconds")
      .groupBy(
        window($"timestamp", "1 seconds", "1 seconds"),
        $"metric_name", $"module", $"classify", $"host_name")
      .agg(max("value") as "max"
        , min("value") as "min"
        , avg("value") as "avg"
        , count("value") as "cnt")

//    val writer =new DrmSink("get")
//    // 开始运行将 running counts 打印到控制台的查询
//    val query = wordCounts.writeStream
//      .outputMode("update")
//      .foreach(writer)
//      .start()

//    query.awaitTermination()
  }


  case class MonitorEntity(x1:String,x2:String,x3:String);
}
