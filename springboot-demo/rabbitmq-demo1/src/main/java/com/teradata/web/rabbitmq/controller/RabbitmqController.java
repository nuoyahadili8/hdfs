package com.teradata.web.rabbitmq.controller;

import com.teradata.web.rabbitmq.service.FanoutSender;
import com.teradata.web.rabbitmq.service.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/api")
public class RabbitmqController {

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private FanoutSender fanoutSender;

    @RequestMapping("send")
    public String send(){
        rabbitSender.send("hello");
        return "aa";
    }

    @RequestMapping("senda")
    public String senda(){
        fanoutSender.send("hello");
        return "aa";
    }
}
