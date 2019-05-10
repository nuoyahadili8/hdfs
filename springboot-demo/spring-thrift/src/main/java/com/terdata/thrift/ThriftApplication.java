package com.terdata.thrift;

import com.terdata.thrift.service.ThriftServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/11/26/026 Administrator Create 1.0
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
public class ThriftApplication {

    private static ThriftServer thriftServer;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ThriftApplication.class,args);
        try {
            thriftServer = context.getBean(ThriftServer.class);
            thriftServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
