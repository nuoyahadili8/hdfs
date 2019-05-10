package com.teradata.intenet.disruptor.wizard;


import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.teradata.intenet.disruptor.LongEvent;

import java.nio.ByteBuffer;
import java.util.concurrent.*;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/11/16/016 Administrator Create 1.0
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
public class DisruptorWizardMain2 {

    private static final Translator TRANSLATOR = new Translator();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        int bufferSize = 1024;
        EventHandler h1a = new EventHandler<LongEvent>() {
            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("h1a : " + event.getValue());
            }
        };

        EventHandler h1b = new EventHandler<LongEvent>() {

            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("h1b : " + event.getValue() + " arrived. H1a should have completed. Completed.");
            }
        };

        EventHandler h2a = new EventHandler<LongEvent>() {

            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("h2a : " + event.getValue());
            }
        };
        EventHandler h2b = new EventHandler<LongEvent>() {

            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("h2b : " + event.getValue() + " arrived. H2a should have completed. Completed.");
            }
        };

        EventHandler lastHandler = new EventHandler<LongEvent>() {

            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("lastHandler : " + event.getValue() + " arrived. H1a, h1b, h2a and h2b should have completed. Completed.\n");
            }
        };

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(new EventFactory<LongEvent>(){

            @Override
            public LongEvent newInstance() {
                return new LongEvent();
            }
        }, bufferSize, executor);

        disruptor.handleEventsWith(h1a, h2a);
        disruptor.after(h1a).then(h1b);
        disruptor.after(h2a).then(h2b);
        disruptor.after(h1b, h2b).then(lastHandler);

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; l<1; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent(TRANSLATOR, bb);
            Thread.sleep(1000);
        }

        executor.shutdown();
        disruptor.shutdown();

    }
}
