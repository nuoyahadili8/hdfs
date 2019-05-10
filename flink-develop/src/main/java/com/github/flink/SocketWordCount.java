package com.github.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/28/028 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class SocketWordCount {

    public static void main(String[] args) throws Exception {
        int port;

        try{
            ParameterTool parameterTool = ParameterTool.fromArgs(args);
            port = parameterTool.getInt("port");
        }catch (Exception e){
            port = 9999;
        }

        //获取flink的运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        String hostname= "192.168.0.134";
        String delimiter = "\n";
        DataStream<String> text = env.socketTextStream(hostname, port, delimiter);

        DataStream<WordCount> sum = text.flatMap(new FlatMapFunction<String, WordCount>() {
            @Override
            public void flatMap(String s, Collector<WordCount> collector) throws Exception {
                String[] split = s.split("\\s");
                for (String word : split) {
                    collector.collect(new WordCount(word, 1L));
                }
            }
        }).keyBy("word")
                //指定窗口大小为2s
                .timeWindow(Time.seconds(2), Time.seconds(1))
                // sum reduce都可以
                .sum("count");

        sum.print().setParallelism(1);
        //这行代码必须有，否则不执行
        env.execute("Socket word count");
    }

    public static class WordCount{
        public String word;
        public long count;
        public WordCount(){}
        public WordCount(String word,long count){
            this.word=word;
            this.count=count;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
