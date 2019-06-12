package com.github.al.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.al.dubbo.api.FindWordService;

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
        version = "1.0.0",
        timeout = 5000
)
public class FindWordServiceImpl implements FindWordService {
    @Override
    public String findString(String str) {
        System.out.println("服务端被调用了find!");
        return "find!" + str;
    }
}
