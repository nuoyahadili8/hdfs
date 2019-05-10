package com.teradata.spark2x.struct

import org.apache.spark.sql.SparkSession

/**
  * @Project:
  * @Description:
  * @Version 1.0.0
  * @Throws SystemException:
  * @Author: <li>2018/12/2/002 Administrator Create 1.0
  * @Copyright ©2018-2019 al.github
  * @Modified By:
  */
object StructStreamingDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.
      master("local")
      .appName("example")
      .getOrCreate()

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
      .option("subscribe", "topic1")
      .load()

//    df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
//      .as[(String, String)]

    spark.stop()

  }


}
