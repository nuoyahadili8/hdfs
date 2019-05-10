package com.teradata.intenet.zookeeper.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/12/012 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class MasterSelector {

    public static final String CONNECTSTRING="127.0.0.1:2181";

    public final static String MASTER_PATH="/curator_master_path";

    public static void main(String[] args) {
        CuratorFramework curatorFramework= CuratorFrameworkFactory.builder().connectString(CONNECTSTRING)
                .retryPolicy(new ExponentialBackoffRetry(100,3)).build();

        LeaderSelector leaderSelector=new LeaderSelector(curatorFramework, MASTER_PATH, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("获得leader成功！");
                TimeUnit.SECONDS.sleep(2);
            }
        });

        leaderSelector.autoRequeue();
        leaderSelector.start(); //开始选举
    }
}
