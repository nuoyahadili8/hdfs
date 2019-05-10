package com.teradata.web.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/11/14/014 Administrator Create 1.0
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
@Configuration
public class FanoutRabbitMQConfig {

    //默认持久化durable为true
    //根据方法名来进行绑定的 firstFanoutQueue()
    @Bean
    public Queue firstFanoutQueue() {
        return new Queue("firstFanoutQueue");
    }

    @Bean
    public Queue secondFanoutQueue() {
        return new Queue("secondFanoutQueue");
    }

    //默认持久化durbale为true
    //exchange交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 以下两种方法都可以成功
     */
    @Bean
    public Binding bindingFirst() {
        return BindingBuilder.bind(firstFanoutQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingSecond(FanoutExchange fanoutExchange, Queue secondFanoutQueue) {
        return BindingBuilder.bind(secondFanoutQueue).to(fanoutExchange);
    }
}
