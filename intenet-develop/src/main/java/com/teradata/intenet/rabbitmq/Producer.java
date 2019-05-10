package com.teradata.intenet.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private static final String QUEUE_NAME ="queue.test";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("106.12.203.43");
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("123qwe");
        //创建一个连接
        Connection connection = connectionFactory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //声明队列
        //queueDeclare第一个参数表示队列名称、
        // 第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
        // 第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）、
        // 第四个参数为当所有消费者客户端连接断开时是否自动删除队列、第五个参数为队列的其他参数
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg = "hello rabbit";
        //发送消息到队列
        //basicPublish第一个参数为交换机名称、
        // 第二个参数为队列映射的路由key、
        // 第三个参数为消息的其他属性、
        // 第四个参数为发送信息的主体
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes("UTF-8"));
        System.out.println("Producer Send +'" + msg + "'");
        channel.close();
        connection.close();
    }
}
