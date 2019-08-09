package com.teradata.spark2x

import org.apache.spark.sql.SparkSession

/**
  * @Project:
  * @Description:
  * @Version 1.0.0
  * @Throws SystemException:
  * @Author: <li>2018/11/26/026 Administrator Create 1.0
  * @Copyright ©2017-2017
  *            声明：本项目是关于Hive、HBase的个人爱好代码。与
  *            !@#$**中!@#$**央!@#$**国!@#$**债!@#$**、
  *            !@#$**中!@#$**共!@#$**中!@#$**央!@#$**、
  *            !@#$**公!@#$**安!@#$**部!@#$**、
  *            !@#$**中!@#$**央!@#$**结!@#$**算!@#$**、
  *            !@#$**t!@#$**e!@#$**r!@#$**a!@#$**d
  *            !@#$**a!@#$**t!@#$**a!@#$**无半点关系，特此声明！
  * @Modified By:
  */
object TopKLocal {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.
      master("local")
      .appName("example")
      .getOrCreate()
    val sc=sparkSession.sparkContext
    sc.makeRDD(1 to 10).collect()
    val lines = sc.textFile("D://flinkx_hdfs.json", 1)
    val result = lines.flatMap(_.split("\\s+")).map((_, 1)).reduceByKey(_ + _)
    val sorted = result.map { case (key, value) => (value, key) }.sortByKey(true, 1)
    val topk = sorted.top(10) //10自己定义
    topk.foreach(println)
    sc.stop
  }
}
