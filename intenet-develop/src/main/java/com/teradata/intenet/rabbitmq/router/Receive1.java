package com.teradata.intenet.rabbitmq.router;

import com.rabbitmq.client.*;
import com.teradata.intenet.rabbitmq.ConnectionUtil;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive1 {
    public static final String QUEUE_NAME = "test_queue_direct_1";
    public static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.获取连接
        Connection connection = ConnectionUtil.getConnection("106.12.203.43", 5672, "/", "root", "123qwe!!");
        //2.声明通道
        Channel channel = connection.createChannel();
        //3.声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"key3");
        //同一时刻服务器只会发送一条消息给消费者
        channel.basicQos(1);

        //4.定义队列的消费者
        Consumer consumer = new DefaultConsumer(channel){
            /**
             * envelope主要存放生产者相关信息（比如交换机、路由key等）body是消息实体。
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Customer Received '" + message + "'");
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(QUEUE_NAME,true, consumer);
    }
}
