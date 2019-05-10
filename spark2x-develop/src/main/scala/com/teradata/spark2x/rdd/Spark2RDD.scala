package com.teradata.spark2x.rdd

import org.apache.spark.sql.SparkSession

object Spark2RDD {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.
      master("local")
      .appName("example")
      .getOrCreate()
    val sc = sparkSession.sparkContext
    val rdd1=sc.parallelize(Array(("1|2","460003411585776|1111"),("1|2","460024065426719|2222")))

    val rdd2=sc.parallelize(Array(("2|2","460003411585776|3333"),("2|2","460024065426719|4444")))

    val rdd4=sc.parallelize(Array(("1|2","1"),("2|2","1")))

    val rdd3=rdd1.union(rdd2).join(rdd4).map(column=>{
      val value=column._2._1.split("[|]")
      (column._2._2+"|"+value(0),value(1))
    })

    val rdd5=rdd3.reduceByKey((first:String,second:String) =>{
      if(first.compareToIgnoreCase(second)<=0) first else second
    })

    rdd3.collect.foreach(println)

    rdd5.collect.foreach(println)

    val rdd6=sc.parallelize(Array(("1|460003411585776","3333"),("1|460024065426719","4444")))
    rdd6.collect.foreach(println)

    val rdd7=rdd6.leftOuterJoin(rdd5)
    rdd7.collect.foreach(println)

    sparkSession.stop()
  }

}
