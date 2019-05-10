package com.terdata.thrift.config;

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
import com.terdata.thrift.client.JazzClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JazzClientConfig {
    @Value("${thrift.host}")
    private String host;
    @Value("${thrift.port}")
    private int port;

    @Bean(initMethod = "init")
    public JazzClient jazzClient() {
        JazzClient jazzClient = new JazzClient();
        jazzClient.setHost(host);
        jazzClient.setPort(port);
        return jazzClient;
    }
}
