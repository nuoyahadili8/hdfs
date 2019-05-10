package com.teradata.intenet.disruptor.wizard;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.teradata.intenet.disruptor.LongEvent;

import java.nio.ByteBuffer;

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
public class Translator implements EventTranslatorOneArg<LongEvent, ByteBuffer> {
    @Override
    public void translateTo(LongEvent event, long sequence, ByteBuffer data) {
        event.setValue(data.getLong(0));
    }
}
