package com.github.al.dubbo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/11/011 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
@SpringBootApplication
@ComponentScan("com.github.al.dubbo")
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
