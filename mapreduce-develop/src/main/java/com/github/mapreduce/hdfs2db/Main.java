package com.github.mapreduce.hdfs2db;

import com.github.mapreduce.bean.User;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

/**
 * @Description:
 * @Date: Created in 2018-06-14 15:40
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        //mysql的jdbc驱动
        DBConfiguration.configureDB(conf,"com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/db1?useUnicode=true&characterEncoding=utf-8&useSSL=false",
                "root",
                "123qwe");

        Job job = Job.getInstance(conf);

        job.setJarByClass(Main.class);

        job.setMapperClass(Hdfs2dbMapper.class);

        job.setReducerClass(Hdfs2dbReducer.class);

        job.setMapOutputKeyClass(User.class);

        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(User.class);

        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job,new Path(args[0]));

        job.setOutputFormatClass(DBOutputFormat.class);

        String[] fields = {"user_id","user_name","user_age"};

        DBOutputFormat.setOutput(job,"user",fields);

        System.exit(job.waitForCompletion(true)? 0 : 1);

    }

}
