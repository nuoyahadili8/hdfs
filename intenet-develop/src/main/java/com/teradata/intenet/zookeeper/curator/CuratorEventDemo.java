package com.teradata.intenet.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/8/008 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class CuratorEventDemo {

    /**
     * 三种watcher来做节点的监听
     * pathcache  监视一个路径下子节点的创建、删除、节点数据更新
     * NodeCache 监视一个节点的创建、更新、删除
     * TreeCache pathcache+NodeCache的合体（监视路径下的创建、更新、删除事件），缓冲路径下的所有子节点的数据
     */
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework=CuratorClientUtils.getInstance();

        NodeCache cache=new NodeCache(curatorFramework,"/curator",false);
        cache.start(true);

        cache.getListenable().addListener(()-> System.out.println("节点数据发生变化，变化后的结果"+new String(cache.getCurrentData().getData())));

        curatorFramework.setData().forPath("/curator","2121".getBytes());

        System.in.read();
    }
}
