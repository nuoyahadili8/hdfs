package com.teradata.spark2x.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

/**
  * @Project:
  * @Description:
  * @Version 1.0.0
  * @Throws SystemException:
  * @Author: <li>2019/8/9/009 Administrator Create 1.0
  * @Copyright ©2018-2019 al.github
  * @Modified By:
  */
object SparkStreamingKafkaScala {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("kafka")
    conf.setMaster("local[*]")

    val ssc = new StreamingContext(conf , Seconds(2))

    //kafka参数
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "s102:9092,s103:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "g1",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("topic1")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    val ds2 = stream.map(record => (record.key, record.value))
    ds2.print()

    ssc.start()

    ssc.awaitTermination()
  }
}
