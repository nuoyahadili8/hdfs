package com.teradata.intenet.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/5/005 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class ApiOperatorDemo implements Watcher{

    public static final String CONNECTSTRING="127.0.0.1:2181";

    public static CountDownLatch countDownLatch=new CountDownLatch(1);

    private static ZooKeeper zooKeeper;

    private static Stat stat=new Stat();

    public static void main(String[] args) throws Exception {

        zooKeeper=new ZooKeeper(CONNECTSTRING, 5000,new ApiOperatorDemo());

        countDownLatch.await();
        System.out.println(zooKeeper.getState());

        //创建回话临时目录
        String result=zooKeeper.create("/node1","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData("/node1",new ApiOperatorDemo(),stat); //增加一个watcher
        System.out.println(result);

        //修改数据
        zooKeeper.setData("/node1","aa".getBytes(),-1);
        Thread.sleep(3000);

        //删除节点
        zooKeeper.delete("/node1",-1);
        TimeUnit.SECONDS.sleep(2);

        //创建节点和子节点
        String newPath="/node2";

        Stat exists = zooKeeper.exists(newPath, true);
        if (exists==null){  //表示节点不存在
            zooKeeper.create(newPath,"123".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }

        Thread.sleep(1000);

        zooKeeper.getData(newPath,new ApiOperatorDemo(),stat);
        zooKeeper.create(newPath+"/node","123".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);

        //修改子节点
        zooKeeper.setData(newPath,"aa".getBytes(),-1);
        TimeUnit.SECONDS.sleep(3);


    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState()==Event.KeeperState.SyncConnected){

        }
        if (Event.EventType.None == event.getType() && null==event.getPath()){
            if (event.getState() == Event.KeeperState.SyncConnected){
                countDownLatch.countDown();
                System.out.println(event.getState()+"-->"+event.getType());
            }
        } else if (event.getType() == Event.EventType.NodeDataChanged){
            try {
                System.out.println("路径："+event.getPath()+" 改变后的值："+zooKeeper.getData(event.getPath(),true,stat));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (event.getType() == Event.EventType.NodeChildrenChanged){
            try {
                System.out.println("子节点数据变更路径："+event.getPath()+"->节点的值：" + zooKeeper.getData(event.getPath(),true,stat));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (event.getType() == Event.EventType.NodeCreated){
            try {
                System.out.println("路径："+event.getPath()+" 节点的值："+zooKeeper.getData(event.getPath(),true,stat));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (event.getType() == Event.EventType.NodeDeleted){
            System.out.println("节点删除路径："+event.getPath());
        }

        System.out.println(event.getType());
    }
}
