package com.teradata.intenet.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Project:
 * @Description: 如果两个线程有一个没有执行exchange()方法，则会一直等待，如果担心有特殊情况发
 * 生，避免一直等待，可以使用exchange（V x，longtimeout，TimeUnit unit）设置最大等待时长。
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/3/003 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class ExchangerTest {

    private static final Exchanger<String>exgr = new Exchanger<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String A = "银行流水A"; // A录入银行流水数据
                    exgr.exchange(A);
                } catch (InterruptedException e) {
                }
            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String B = "银行流水B"; // B录入银行流水数据
                    String A = exgr.exchange(B);
                    System.out.println("A和B数据是否一致：" + A.equals(B) + "，A录入的是："
                            + A + "，B录入是：" + B);
                } catch (InterruptedException e) {
                }
            }
        });
        threadPool.shutdown();
    }
}
