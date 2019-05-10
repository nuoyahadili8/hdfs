package com.teradata.spark2x

import com.alibaba.fastjson.JSON
import org.apache.hadoop.hdfs.web.JsonUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * @Project:
  * @Description:
  * @Version 1.0.0
  * @Throws SystemException:
  * @Author: <li>2019/2/11/011 Administrator Create 1.0
  * @Copyright ©2018-2019 al.github
  * @Modified By:
  */
object TagRDD {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.
      master("local")
      .appName("TagRDD")
      .getOrCreate()
    val sc = sparkSession.sparkContext

    //加载文件
    val rddl = sc.textFile("file:///d:/temptags.txt")

    //切割行，过滤无效行
    val rdd2 = rddl.map(e=>e.split("\t")).filter(_.length > 0)

    //解折json [busid , json]
    val rdd3 = rdd2.map(e=>(e(0),JSON.parse(e(1))))



  }

}
