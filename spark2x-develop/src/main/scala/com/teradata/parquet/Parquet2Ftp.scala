package com.teradata.parquet

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat

/**
  * @Project:
  * @Description:
  * @Version 1.0.0
  * @Throws SystemException:
  * @Author: <li>2019/2/17/017 Administrator Create 1.0
  * @Copyright ©2018-2019 al.github
  * @Modified By:
  */
object Parquet2Ftp {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.
      master("local")
      .appName("ParquetRdd")
      .getOrCreate()

    val sc=spark.sparkContext

    val parquetFileDF = spark.read.parquet("file:///D:/000000_0")

    import spark.implicits._
    parquetFileDF.createOrReplaceTempView("parquetFile")
    val namesDF = spark.sql("SELECT deal_date FROM parquetFile")

    println(namesDF.collect().size)

  }

}
