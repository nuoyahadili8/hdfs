package com.github.mapreduce.hdfs2db;

import com.github.mapreduce.bean.User;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Description:
 * @Date: Created in 2018-06-14 15:40
 */
public class Hdfs2dbMapper extends Mapper<LongWritable,Text, User,NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String str=value.toString();
        String [] strs=str.split("\t");
        context.write(new User(Integer.parseInt(strs[0]),strs[1],Integer.parseInt(strs[2])),NullWritable.get());
    }
}
