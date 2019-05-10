package com.github.mapreduce.hdfs2db;

import com.github.mapreduce.bean.User;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

/**
 * @Description:
 * @Date: Created in 2018-06-14 15:40
 */
public class Hdfs2dbReducer extends Reducer<User,NullWritable,User,NullWritable>{

    @Override
    protected void reduce(User key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        for(NullWritable nullWritable:values){
            context.write(key,nullWritable);
        }
    }
}
