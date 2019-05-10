package com.teradata.intenet.rabbitmq.work;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/13/013 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
import com.rabbitmq.client.*;
import com.teradata.intenet.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <一句话描述>,
 * <详细介绍>,
 *
 */
public class Consumer2 {
    public static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection("106.12.203.43", 5672, "/", "root", "123qwe!!");
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
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
