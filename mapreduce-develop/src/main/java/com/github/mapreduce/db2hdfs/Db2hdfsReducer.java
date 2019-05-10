package com.github.mapreduce.db2hdfs;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-05-16 15:58
 */

public  class Db2hdfsReducer extends Reducer<LongWritable,Text,LongWritable,Text> {
    @Override
    protected void reduce(LongWritable key, Iterable<Text> values,
                          Context context)
            throws IOException, InterruptedException {
        for(Iterator<Text> itr = values.iterator(); itr.hasNext();)
        {
            context.write(key, itr.next());
        }
    }
}