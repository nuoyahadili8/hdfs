package com.teradata.intenet.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 */
public class UseThreadPoolExecutor implements Runnable{

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        try {
            int temp = count.incrementAndGet();
            System.out.println("任务" + temp);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ExecutorService executor  = new ThreadPoolExecutor(
                5, 		//core
                10, 	//max
                120L, 	//2fenzhong
                TimeUnit.SECONDS,
                queue);
        for(int i = 0 ; i < 20; i++){
            executor.execute(new UseThreadPoolExecutor());
        }
        Thread.sleep(1000);
        System.out.println("queue size:" + queue.size());		//10
        Thread.sleep(2000);
        executor.shutdown();
    }
}
