package com.teradata.flink

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.core.fs.FileSystem.WriteMode
import org.apache.flink.api.scala._

object WordCount {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.createLocalEnvironment(1)
    //从本地读取文件
    val text = env.readTextFile("D:/flinkx_hdfs.json")

    //单词统计
    val counts = text.flatMap{ _.toLowerCase.split("\\W+").filter { _.nonEmpty } }
      .map { (_, 1) }
      .groupBy(0)
      .sum(1)

    //输出结果
    counts.print()

    //保存结果到txt文件
    counts.writeAsText("D:/output.txt", WriteMode.OVERWRITE)
    env.execute("Scala WordCount Example")
  }

}
