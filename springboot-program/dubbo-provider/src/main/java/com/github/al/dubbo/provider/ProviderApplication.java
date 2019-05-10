package com.github.al.dubbo.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
@EnableDubboConfig
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }
}
