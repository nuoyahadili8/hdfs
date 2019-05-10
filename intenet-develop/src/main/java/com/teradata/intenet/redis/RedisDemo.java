package com.teradata.intenet.redis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/11/11/011 Administrator Create 1.0
 * @Copyright ©2017-2017
 * 声明：本项目是关于Hive、HBase的个人爱好代码。与
 * !@#$**中!@#$**央!@#$**国!@#$**债!@#$**、
 * !@#$**中!@#$**共!@#$**中!@#$**央!@#$**、
 * !@#$**公!@#$**安!@#$**部!@#$**、
 * !@#$**中!@#$**央!@#$**结!@#$**算!@#$**、
 * !@#$**t!@#$**e!@#$**r!@#$**a!@#$**d
 * !@#$**a!@#$**t!@#$**a!@#$**无半点关系，特此声明！
 * @Modified By:
 */
public class RedisDemo {

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis=new Jedis("106.12.203.3",6379);
//        jedis.auth("123qwe");
        jedis.select(1);
        jedis.set("key2","value2");

        jedis.setex("key1",5,"value1");
        System.out.println("key1=="+jedis.get("key1"));
        TimeUnit.SECONDS.sleep(6);
        System.out.println("key1=="+jedis.get("key1"));
        System.out.println("key2=="+jedis.get("key2"));

        jedis.close();
    }
}
