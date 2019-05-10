package com.github.al.limit.aop;

import com.github.al.limit.vo.R;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/19/019 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
@Component
@Scope
@Aspect
public class RateLimitAop {

    @Autowired
    private HttpServletResponse response;

    //比如说，我这里设置"并发数"为5
    private RateLimiter rateLimiter=RateLimiter.create(2);

    @Pointcut("@annotation(com.github.al.limit.aop.RateLimitAspect)")
    public void serviceLimit() {

    }

    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Boolean flag = rateLimiter.tryAcquire();
        Object obj = null;
        try {
            if (flag) {
                obj = joinPoint.proceed();
                System.out.println("flag=" + flag + ",obj=" + obj);
            }else{
                String result = R.error("error").toString();
                output(response, result);
                System.out.println("发生错误！");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return obj;
    }

    public void output(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/text;charset=UTF-8");
        PrintWriter outputStream = null;
        try {
            outputStream = response.getWriter();
            outputStream.append(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream!=null){
                outputStream.close();
            }
        }
    }
}
