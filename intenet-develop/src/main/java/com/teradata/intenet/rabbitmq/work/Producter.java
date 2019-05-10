package com.teradata.intenet.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.teradata.intenet.rabbitmq.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * @Project:
 * @Description: Work模式下的生产者
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/13/013 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class Producter {
    public static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //1.获取连接
        Connection connection = ConnectionUtil.getConnection("106.12.203.43", 5672, "/", "root", "123qwe!!");
        //2.声明信道
        Channel channel = connection.createChannel();
        //3.声明(创建)队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //4.定义消息内容,发布多条消息
        for (int i = 0; i < 50; i++) {
            String message = "hello rabbitmq " + i;
            //5.发布消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("[x] send message is '" + message + "'");
            //6.模拟发送消息延时,便于展示多个消费者竞争接受消息
            Thread.sleep(i * 10);
        }
        //7.关闭信道
        channel.close();
        //8.关闭连接
        connection.close();
    }
}
