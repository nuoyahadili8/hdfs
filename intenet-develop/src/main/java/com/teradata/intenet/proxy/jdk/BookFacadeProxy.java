package com.teradata.intenet.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Project:
 * @Description: JDK动态代理: 利用反射原理,动态的生成代理类,将类的载入延迟到程序执行之中,解耦了代理类和被代理类的联系.主要要实现InvationHandler接口.
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/30/030 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class BookFacadeProxy implements InvocationHandler {

    /**
     * 被代理对象
     */
    private Object target;

    /**
     *
     * 功能描述: <br>
     * 〈功能详细描述〉绑定被代理对象 返回代理对象
     *
     * @param target
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public Object bind(Object target) {
        this.target = target;
        // 要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)
        // 返回代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (method.getName().startsWith("delete")) {
            System.out.println("#####方法执行之前#####");
            result = method.invoke(target, args);
            System.out.println("#####方法执行之后#####");
        } else {
            result = method.invoke(target, args);
        }
        return result;
    }

    public static void main(String[] args) {
        BookFacadeProxy proxy = new BookFacadeProxy();
        IBookFacade bookProxy = (IBookFacade) proxy.bind(new BookFacade());
        bookProxy.addBook("红楼梦");
        bookProxy.deleteBook();
    }
}
