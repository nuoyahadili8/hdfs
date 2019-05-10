package com.github.al.dubbo.api;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/20/020 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class SayHelloServiceMock implements SayHelloService {
    @Override
    public String sayHello(String name) {
        return "sayHello服务降级返回的信息";
    }

    @Override
    public String findString(String name) {
        return "findString服务降级返回的信息";
    }
}
