package com.teradata.intenet.disruptor.generate1;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class TradeHandler implements EventHandler<Trade>, WorkHandler<Trade> {

    private final AtomicInteger cid=new AtomicInteger();
	  
    @Override  
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {  
        this.onEvent(event);  
    }  
  
    @Override  
    public void onEvent(Trade event) throws Exception {  
        //这里做具体的消费逻辑  
        event.setId(UUID.randomUUID().toString());//简单生成下ID
        System.out.println(event.getId());
        System.out.println(cid.incrementAndGet());
    }  
}  