package com.teradata.intenet.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

/**
 * @Project:
 * @Description: 建立代理类
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/30/030 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 * @desc: CGLIB动态代理:原理是继承,把被代理类作为父类,动态生成被代理类的子类,三个步骤,设置父类,设置回调函数,
 * 创建子类.实现MethodInterceptor 接口,拦截调用父类方法时,会处理回调方法,处理自己的增强方法.
 */
public class BookFacadeCglib implements MethodInterceptor {

    private Object target;

    /**
     * 创建代理对象
     *
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("方法执行之前");
        proxy.invokeSuper(obj, args);
        System.out.println("方法执行之后");
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        BookFacadeCglib cglib = new BookFacadeCglib();
        BookFacade bookCglib = (BookFacade) cglib.getInstance(new BookFacade());
        bookCglib.addBook();
    }
}
