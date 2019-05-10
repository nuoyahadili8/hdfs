package com.teradata.intenet.disruptor.demo;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/27/027 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class DisruptorManager {

    private Disruptor<MessageEvent> disruptor;
    private RingBuffer<MessageEvent> ringBuffer;

    public void init() {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "MESSAGE-PUBLISH-THREAD");
            }
        };

        disruptor = new Disruptor<>(new MessageEventFactory(), 8 * 1024, threadFactory, ProducerType.MULTI, new BlockingWaitStrategy());
        ringBuffer = disruptor.getRingBuffer();
        MessageEventHandler messageEventHandler = MessageEventHandler.getSingleIntence();
        disruptor.handleEventsWith(messageEventHandler).then(messageEventHandler);
        disruptor.start();

    }

    public void destroy() {
        if (disruptor != null) {
            disruptor.shutdown();
        }
    }

    public void work(Message message) {
        if (null == ringBuffer) {
            return;
        }
        long next = ringBuffer.next();
        try {
            MessageEvent messageEvent = ringBuffer.get(next);
            messageEvent.setMessage(message);
        } finally {
            ringBuffer.publish(next);
        }
    }

    public static void main(String[] args) {
        DisruptorManager disruptorManager = new DisruptorManager();

        disruptorManager.init();

        Message message = new Message();
        message.setId("1");
        message.setMsg("an");
        disruptorManager.work(message);

        disruptorManager.destroy();
    }

}
