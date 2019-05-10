package com.github.al.limit.service;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/19/019 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
@Service
public class GuavaRateLimiterService {

    //每秒控制5个许可
    RateLimiter rateLimiter = RateLimiter.create(5.0);

    /**
     * 获取令牌
     *
     * @return
     */
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }
}
