package com.teradata.intenet.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZkClientApiOperDemo {
    public static final String CONNECTSTRING="106.12.203.43:2181";

    private static ZkClient getInstance(){
        return new ZkClient(CONNECTSTRING,5000);
    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient=ZkClientApiOperDemo.getInstance();
        zkClient.createEphemeral("/an1");
        System.out.println("success!");


//        zkClient.createPersistent("/an1/an2/al1",true);

        //watcher
        zkClient.subscribeDataChanges("/an1", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("节点名称："+s+"->节点值："+o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });

        zkClient.writeData("/an1","332");

        Thread.sleep(5000);
    }
}
