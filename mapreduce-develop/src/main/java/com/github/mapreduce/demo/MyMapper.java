package com.github.mapreduce.demo;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/26/026 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class MyMapper extends Mapper<Object, Text, DBOutput, IntWritable> {

    private static final IntWritable one = new IntWritable(1);
    private DBOutput text = new DBOutput();
    @Override
    public void map(Object key, Text value, Mapper<Object, Text, DBOutput, IntWritable>.Context context)
            throws IOException, InterruptedException{
        StringTokenizer token = new StringTokenizer(value.toString().replaceAll("\\p{Punct}|\\d", "").toLowerCase());
        while(token.hasMoreTokens()) {
            this.text.setText(token.nextToken());
            context.write(this.text, one);
        }

    }
}
