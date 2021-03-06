package com.teradata.intenet.classload;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/27/027 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader(Thread.currentThread().getContextClassLoader(),"D:/IdeaProjects/hdfs/intenet-develop/target/classes/");

        Class<?> clazz = myClassLoader.findClass("com.teradata.intenet.classload.Hello");

        Hello hello = (Hello) clazz.newInstance();

        hello.say();

    }
}
