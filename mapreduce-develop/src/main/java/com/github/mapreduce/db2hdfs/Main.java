package com.github.mapreduce.db2hdfs;

import com.github.mapreduce.bean.User;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-05-14 19:21
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        //mysql的jdbc驱动
        DBConfiguration.configureDB(conf,"com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.0.132:3306/db1?useUnicode=true&characterEncoding=utf-8&useSSL=false",
                "root",
                "123qwe");

        Job job = Job.getInstance(conf);

        job.setJarByClass(Main.class);

        job.setMapperClass(Db2hdfsMapper.class);

        job.setReducerClass(Db2hdfsReducer.class);

        job.setOutputKeyClass(LongWritable.class);

        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(DBInputFormat.class);

        FileOutputFormat.setOutputPath(job, new Path("hdfs://127.0.0.1:9000/db2hdfs"));

        //对应数据库中的列名(实体类字段)
        String[] fields = {"user_id","user_name","user_age"};

        DBInputFormat.setInput(job, User.class,"user", "", "", fields);

        System.exit(job.waitForCompletion(true)? 0 : 1);

    }

}