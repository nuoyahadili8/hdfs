package com.github.al.limit.controller;

import com.github.al.limit.aop.RateLimitAspect;
import com.github.al.limit.service.GuavaRateLimiterService;
import com.github.al.limit.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/19/019 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private GuavaRateLimiterService rateLimiterService;

    @ResponseBody
    @RequestMapping("/ratelimiter")
    public R testRateLimiter(){
        if(rateLimiterService.tryAcquire()){
            return R.ok().put("data","成功获取许可");
        }
        return R.error().put("data","未获取到许可");
    }

    /**
     * 可以非常方便的通过这个注解来实现限流
     */
    @ResponseBody
    @RateLimitAspect
    @RequestMapping("/aoplimit")
    public String test(){
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return R.ok().put("data", "success").toString();
    }
}
