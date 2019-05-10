package com.teradata.spring.hadoop;

import org.apache.hadoop.fs.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hadoop.fs.FsShell;

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
@SpringBootApplication
public class SpringBootHDFSApplication implements CommandLineRunner {

    @Autowired
    FsShell fsShell;


    @Override
    public void run(String... args) throws Exception {
        for (FileStatus fileStatus:fsShell.lsr("/springhdfs")){
            System.out.println(">"+fileStatus.getPath());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHDFSApplication.class,args);
    }
}
