package com.teradata.intenet.disruptor.demo;

import com.lmax.disruptor.EventFactory;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/8/008 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class MessageEventFactory implements EventFactory<MessageEvent> {

    @Override
    public MessageEvent newInstance() {
        return new MessageEvent();
    }

}
