package com.github.al.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.al.dubbo.api.SayHelloService;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/11/011 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class SayHelloServiceImpl implements SayHelloService {
    @Override
    public String sayHello(String name) {
        System.out.println("服务被调用了！");
        return "Hello! " + name;
    }

    @Override
    public String findString(String name) {
        System.out.println("服务调用findString！");
        return "find! " + name;
    }
}
