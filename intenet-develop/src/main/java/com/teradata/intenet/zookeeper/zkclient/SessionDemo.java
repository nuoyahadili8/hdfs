package com.teradata.intenet.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/5/005 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class SessionDemo {

    public static final String CONNECTSTRING="127.0.0.1:2181";

    public static void main(String[] args) {
        //创建连接
        ZkClient zkClient=new ZkClient(CONNECTSTRING,4000);
        System.out.println(zkClient + "->success");


    }
}
