package com.teradata.flink.kafka

import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.util.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.windowing.time.Time

object ReadFromKafka {
  def main(args: Array[String]) {
    System.out.println("use command as: ")
    System.out.println("./bin/flink run --class com.teradata.flink.kafka.ReadFromKafka" +
      " /opt/test.jar --topic test1 -bootstrap.servers 106.12.203.43:9092")
    System.out.println("******************************************************************************************")
    System.out.println("<topic> is the kafka topic name")
    System.out.println("<bootstrap.servers> is the ip:port list of brokers")
    System.out.println("******************************************************************************************")


//    ./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test1
//    ./kafka-console-producer.sh --broker-list localhost:9092 --topic test1
//    ./kafka-console-consumer.sh --zookeeper localhost:2181 --topic test1 --from-beginning
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val paraTool = ParameterTool.fromArgs(args)
//    paraTool.getProperties.setProperty("bootstrap.servers","106.12.203.43:9092")
//    paraTool.getProperties.setProperty("zookeeper.connect","106.12.203.43:2181")
    val messageStream = env.addSource(new FlinkKafkaConsumer010(
      paraTool.get("topic"), new SimpleStringSchema, paraTool.getProperties))
    val windowCounts=messageStream
      .map { w => WordWithCount(w, 1) }
      .keyBy("word")
      .timeWindow(Time.seconds(5))
      .sum("count")
    windowCounts.print().setParallelism(1)
//      messageStream.map(s => "Flink says " + s + System.getProperty("line.separator")).print()
    env.execute()
  }

  case class WordWithCount(word: String, count: Long)
}
