package com.teradata.spring.hadoop;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/11/20/020 Administrator Create 1.0
 * @Copyright ©2017-2017
 * 声明：本项目是关于Hive、HBase的个人爱好代码。与
 * !@#$**中!@#$**央!@#$**国!@#$**债!@#$**、
 * !@#$**中!@#$**共!@#$**中!@#$**央!@#$**、
 * !@#$**公!@#$**安!@#$**部!@#$**、
 * !@#$**中!@#$**央!@#$**结!@#$**算!@#$**、
 * !@#$**t!@#$**e!@#$**r!@#$**a!@#$**d
 * !@#$**a!@#$**t!@#$**a!@#$**无半点关系，特此声明！
 * @Modified By:
 */

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 使用Spring Hadoop来访问HDFS文件系统
 */
public class SpringHadoopHDFS {

    private ApplicationContext ctx;

    private FileSystem fs;

    /**
     * 创建HDFS文件夹
     * @throws Exception
     */
    public void testMkdir() throws Exception{
        fs.mkdirs(new Path("/springhdfs"));
    }

    /**
     * 读取HDFS文件内容
     */
    public void testReadHD() throws Exception{
        FSDataInputStream in = fs.open(new Path("/springhdfs/a.txt"));
        IOUtils.copyBytes(in,System.out,1024);
        in.close();
    }

    @Before
    public void setUp(){
        ctx = new ClassPathXmlApplicationContext("beans.xml");
        fs = (FileSystem) ctx.getBean("fileSystem");
    }

    @After
    public void tearDown() throws IOException {
        ctx = null;
        fs.close();
    }
}
