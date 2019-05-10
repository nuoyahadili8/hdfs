package com.teradata.intenet.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/5/005 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class CuratorSessionDemo {

    public static final String CONNECTSTRING="127.0.0.1:2181";

    public static void main(String[] args) {
        //创建会话的两种方式
        //sessionTimeoutMs:session超时时间
        //connectionTimeoutMs:连接超时
        //
        CuratorFramework curatorFramework= CuratorFrameworkFactory.newClient(CONNECTSTRING,5000,5000,
                new ExponentialBackoffRetry(1000,3));
        //启动连接
        curatorFramework.start();

        //flunet风格
        CuratorFramework curatorFramework1=CuratorFrameworkFactory.builder().connectString(CONNECTSTRING).sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .namespace("/curator")  //指定会话操作的根目录
                .build();
        curatorFramework1.start();
        System.out.println("success");
    }
}
