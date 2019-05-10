package com.github.mapreduce.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/26/026 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class Driver {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        DBConfiguration.configureDB(conf,
                "com.mysql.jdbc.Driver",   // driver class
                "jdbc:mysql://localhost:3306/wc", // db url
                "root",    // user name
                "123qwe"); //password
        Job job = Job.getInstance(conf);
        job.setJobName("WordCount_DB");
        job.setJarByClass(Driver.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);
        job.setCombinerClass(MyReducer.class);
        job.setMapOutputKeyClass(DBOutput.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(DBOutput.class);
        job.setOutputValueClass(IntWritable.class);
        job.setOutputFormatClass(DBOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        DBOutputFormat.setOutput(job, "word_count", "word", "count");

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
