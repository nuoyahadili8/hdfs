package com.github.al.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.al.dubbo.api.FindWordService;
import com.github.al.dubbo.api.SayHelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/11/011 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
@RestController
@RequestMapping("/dubbo")
public class TestDubboController {

    @Reference(version = "1.0.0", check = true,
            loadbalance = "roundrobin", //负载均衡
            retries = 2, //失败重试次数
            mock = "true" //服务降级
    )
    private SayHelloService sayHelloService;

    @Reference(version = "1.0.0",
            check = true,
            loadbalance = "roundrobin", //负载均衡
            retries = 2, //失败重试次数
            mock = "true"    //服务降级
            )
    private FindWordService findWordService;

    @RequestMapping("say")
    public String hello(String name){
        String s = sayHelloService.sayHello(name);
        return s;
    }


    @RequestMapping("find")
    public String find(String str){
        String string = findWordService.findString(str);
        return string;
    }
}
