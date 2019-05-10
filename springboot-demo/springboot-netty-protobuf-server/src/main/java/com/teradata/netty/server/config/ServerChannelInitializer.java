package com.teradata.netty.server.config;

import com.teradata.netty.server.common.protobuf.Message;
import com.teradata.netty.server.handler.IdleServerHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
//import com.netty.common.protobuf.Message.MessageBase;
import java.util.concurrent.TimeUnit;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/11/27/027 Administrator Create 1.0
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
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final static int READER_IDLE_TIME_SECONDS = 20;//读操作空闲20秒
    private final static int WRITER_IDLE_TIME_SECONDS = 20;//写操作空闲20秒
    private final static int ALL_IDLE_TIME_SECONDS = 40;//读写全部空闲40秒

    @Autowired
    @Qualifier("authServerHandler")
    private ChannelInboundHandlerAdapter authServerHandler;

    @Autowired
    @Qualifier("logicServerHandler")
    private ChannelInboundHandlerAdapter logicServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();

        p.addLast("idleStateHandler", new IdleStateHandler(READER_IDLE_TIME_SECONDS
                , WRITER_IDLE_TIME_SECONDS, ALL_IDLE_TIME_SECONDS, TimeUnit.SECONDS));
        p.addLast("idleTimeoutHandler", new IdleServerHandler());

        p.addLast(new ProtobufVarint32FrameDecoder());
        p.addLast(new ProtobufDecoder(Message.MessageBase.getDefaultInstance()));

        p.addLast(new ProtobufVarint32LengthFieldPrepender());
        p.addLast(new ProtobufEncoder());

        p.addLast("authServerHandler", authServerHandler);
        p.addLast("hearableServerHandler", logicServerHandler);
    }
}
