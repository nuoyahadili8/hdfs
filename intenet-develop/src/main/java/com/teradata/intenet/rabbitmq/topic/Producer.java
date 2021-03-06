package com.teradata.intenet.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("106.12.203.43");
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("123qwe!!");
        //创建一个连接
        Connection connection = connectionFactory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        String msg = "hello rabbit11";
        //发送消息到队列
        channel.basicPublish(EXCHANGE_NAME,"key1.update",null,msg.getBytes());
        System.out.println("Producer Send +'" + msg + "'");
        channel.close();
        connection.close();
    }
}
