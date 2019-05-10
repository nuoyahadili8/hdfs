package com.teradata.intenet.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/5/005 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class AuthControlDemo implements Watcher{

    public static final String CONNECTSTRING="127.0.0.1:2181";

    public static CountDownLatch countDownLatch=new CountDownLatch(1);
    public static CountDownLatch countDownLatch1=new CountDownLatch(1);

    private static ZooKeeper zooKeeper;

    private static Stat stat=new Stat();

    public static void main(String[] args) throws Exception {
        zooKeeper=new ZooKeeper(CONNECTSTRING, 5000,new AuthControlDemo());
        countDownLatch.await();

        ACL acl1 = new ACL(ZooDefs.Perms.CREATE, new Id("digest", "root:root"));
        ACL acl2 = new ACL(ZooDefs.Perms.CREATE, new Id("ip", "192.168.0.104"));

        List<ACL> acls=new ArrayList<>();
        acls.add(acl1);
        acls.add(acl2);

        zooKeeper.create("/auth1","111".getBytes(),acls,CreateMode.PERSISTENT);

        Thread.sleep(3000);

        ZooKeeper newZookeeper=new ZooKeeper(CONNECTSTRING, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (Event.EventType.None == event.getType() && null==event.getPath()){
                    if (event.getState() == Event.KeeperState.SyncConnected){
                        countDownLatch1.countDown();
                        System.out.println(event.getState()+"-->"+event.getType());
                    }
                }
            }
        });
        countDownLatch1.await();
        newZookeeper.delete("/auth1",-1);
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.EventType.None == event.getType() && null==event.getPath()){
            if (event.getState() == Event.KeeperState.SyncConnected){
                countDownLatch.countDown();
                System.out.println(event.getState()+"-->"+event.getType());
            }
        }
    }
}
