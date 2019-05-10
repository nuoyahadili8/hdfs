package com.teradata.intenet.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.transaction.CuratorTransactionBridge;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.*;

public class CuratorClientUtils {
//   代码参考： http://www.cnblogs.com/coder-lzh/p/9523297.html

    public static final String CONNECTSTRING="127.0.0.1:2181";

    private static CuratorFramework curatorFramework;

    public static CuratorFramework getInstance(){
        curatorFramework=CuratorFrameworkFactory.newClient(CONNECTSTRING,5000,5000,
                new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
        return curatorFramework;
    }

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorClientUtils.getInstance();

        System.out.println("连接成功。。。。。。");

        /**
         * 创建节点
         */
//        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
//                .forPath("/curator/curator1/curator11","123".getBytes());

        /**
         * 删除节点
         */
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/event");

        /**
         * 查询
         */
//        Stat stat=new Stat();
//        byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/an1");
//        System.out.println(stat);

        /**
         * 更新
         */
//        Stat stat1 = curatorFramework.setData().forPath("/an1", "2323".getBytes());
//        System.out.println(stat1);

        /**
         * 异步操作
         */
//        ExecutorService executorService= Executors.newFixedThreadPool(1);
//        CountDownLatch count=new CountDownLatch(1);
//        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
//                .inBackground(new BackgroundCallback() {
//                    @Override
//                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
//                        System.out.println(Thread.currentThread().getName()+"resultCode:"+event.getResultCode()+"->"+event.getType());
//                        count.countDown();
//                    }
//                },executorService).forPath("/node2","11".getBytes());
//
//        count.await();
//        executorService.shutdown();

        /**
         * 事务操作(Curator独有)
         * 绑定2个操作到一个事务中
         */
//        Collection<CuratorTransactionResult> resultCollections= curatorFramework.inTransaction().create().forPath("/aa","11".getBytes()).and()
//                .setData().forPath("/an1","11".getBytes()).and().commit();
//
//        for (CuratorTransactionResult result:resultCollections){
//            System.out.println(result.getForPath()+"->"+result.getType());
//        }

        /**
         * PatchChildrenCache
         */
        PathChildrenCache cache = new PathChildrenCache(curatorFramework,"/event",true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        cache.getListenable().addListener((curatorFramework1,pathChildrenCacheEven)->{
            switch (pathChildrenCacheEven.getType()){
                case CHILD_ADDED:
                    System.out.println("增加子节点！");
                    break;
                case CHILD_REMOVED:
                    System.out.println("删除子节点");
                    break;
                case CHILD_UPDATED:
                    System.out.println("更新子节点");
                    break;
                default:break;
            }
        });

        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/event","event".getBytes());
        TimeUnit.SECONDS.sleep(1);

        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/event/event1","1".getBytes());
        TimeUnit.SECONDS.sleep(1);

        curatorFramework.setData().forPath("/event/event1","222".getBytes());

        curatorFramework.delete().forPath("/event/event1");

        System.in.read();

    }
}
