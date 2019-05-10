package com.teradata.intenet.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/13/013 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class ConnectionUtil {

    public static Connection getConnection(String host, int port, String vhost, String username, String password) throws IOException, TimeoutException {
        //1.定义连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //2.设置服务器地址
        connectionFactory.setHost(host);
        //3.设置端口
        connectionFactory.setPort(port);
        //4.设置虚拟主机,用户名,密码
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        //5.通过连接工厂获取连接
        Connection connection = connectionFactory.newConnection();
        return connection;
    }
}
