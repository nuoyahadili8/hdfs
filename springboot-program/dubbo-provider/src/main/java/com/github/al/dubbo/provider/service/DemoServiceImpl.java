package com.github.al.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.al.dubbo.api.DemoService;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/6/21/021 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
@Service(
        version = "1.0.0",
        timeout = 5000
)
public class DemoServiceImpl implements DemoService {
    @Override
    public String getString() {
        System.out.println("服务被调用！！！！！");
        return "aaaaaaaaaaaaa";
    }
}
