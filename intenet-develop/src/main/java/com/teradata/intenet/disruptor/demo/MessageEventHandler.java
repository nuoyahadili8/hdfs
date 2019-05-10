package com.teradata.intenet.disruptor.demo;

import com.lmax.disruptor.EventHandler;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/8/008 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class MessageEventHandler implements EventHandler<MessageEvent> {

    private static volatile MessageEventHandler messageEventHandler;

    @Override
    public void onEvent(MessageEvent messageEvent, long arg1, boolean arg2) throws Exception {
        Thread.sleep(5000);
        System.out.println("===========" + messageEvent.getMessage().getId() + "=========" + messageEvent.getMessage().getMsg() + "=======");
        Message message = new Message();
        message.setId("2");
        message.setMsg("liang");
        messageEvent.setMessage(message);
    }

    public static MessageEventHandler getSingleIntence() {
        if (messageEventHandler == null) {
            synchronized (MessageEventHandler.class) {
                if (messageEventHandler == null) {
                    messageEventHandler = new MessageEventHandler();
                }
            }
        }
        return messageEventHandler;
    }

}
