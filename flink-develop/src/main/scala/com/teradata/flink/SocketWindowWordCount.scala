package com.teradata.flink

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * @Project:
  * @Description:
  * @Version 1.0.0
  * @Throws SystemException:
  * @Author: <li>2018/11/19/019 Administrator Create 1.0
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
object SocketWindowWordCount {

  def main(args: Array[String]) : Unit = {

    // the port to connect to
    val port: Int = try {
      ParameterTool.fromArgs(args).getInt("port")
    } catch {
      case e: Exception => {
        System.err.println("No port specified. Please run 'SocketWindowWordCount --port <port>'")
        return
      }
    }

    val host: String = try {
      ParameterTool.fromArgs(args).get("host")
    } catch {
      case e: Exception => {
        System.err.println("No host specified. Please run 'SocketWindowWordCount --host <host>'")
        return
      }
    }

//    val port: Int=9999;

    // get the execution environment
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    // get input data by connecting to the socket
    val text= env.socketTextStream(host, port, '\n')


    // parse the data, group it, window it, and aggregate the counts
    val windowCounts = text
      .flatMap { w => w.split("\\s") }
      .map { w => WordWithCount(w, 1) }
      .keyBy("word")
      .timeWindow(Time.seconds(5), Time.seconds(1))
      .sum("count")

    // print the results with a single thread, rather than in parallel
    windowCounts.print().setParallelism(1)

    env.execute("Socket Window WordCount")
  }

  // Data type for words with count
  case class WordWithCount(word: String, count: Long)

}
