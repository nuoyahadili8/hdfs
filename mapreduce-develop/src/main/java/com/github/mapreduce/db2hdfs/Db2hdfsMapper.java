package com.github.mapreduce.db2hdfs;

/**
 * @Description:
 * @Date: Created in 2018-05-16 15:58
 */

import com.github.mapreduce.bean.User;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public  class Db2hdfsMapper extends Mapper<LongWritable, User,LongWritable,Text> {


    @Override
    protected void map(LongWritable key, User value,Context context)
            throws IOException, InterruptedException {
        context.write(new LongWritable(value.getUserId()), new Text(value.toString()));
    }
}