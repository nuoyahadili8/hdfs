package com.github.yarn;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/10/010 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class Container {

    public static void main(String[] args) throws Exception {
        System.out.println("Container: Initializing");
        System.out.println("Hello World!");
        System.out.println("Container: Finalizing");
        Configuration conf=new Configuration();
//        conf.addResource(new Path("file:///D:\\hadoop_conf\\s3\\core-site.xml"));
//        conf.addResource(new Path("file:///D:\\hadoop_conf\\s3\\hdfs-site.xml"));
//        conf.addResource(new Path("file:///D:\\hadoop_conf\\s3\\yarn-site.xml"));

        FileSystem fileSystem = FileSystem.get(conf);

        fileSystem.mkdirs(new Path("hdfs://localhost:8020/user/root/an"));

        fileSystem.close();
    }
}
