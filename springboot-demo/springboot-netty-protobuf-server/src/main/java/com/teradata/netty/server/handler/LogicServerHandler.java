package com.teradata.netty.server.handler;

import com.teradata.netty.server.common.protobuf.Command;
import com.teradata.netty.server.common.protobuf.Message;
import com.teradata.netty.server.config.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 业务逻辑handler
 */
@Component
@Qualifier("logicServerHandler")
public class LogicServerHandler extends ChannelInboundHandlerAdapter{
	private final AttributeKey<String> clientInfo = AttributeKey.valueOf("clientInfo");

	@Autowired
	@Qualifier("channelRepository")
	ChannelRepository channelRepository;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message.MessageBase msgBase = (Message.MessageBase)msg;

		System.out.println(msgBase.getData());

		ChannelFuture cf = ctx.writeAndFlush(
				Message.MessageBase.newBuilder()
				.setClientId(msgBase.getClientId())
				.setCmd(Command.CommandType.UPLOAD_DATA_BACK)
				.setData("This is upload data back msg")
				.build()
				);
		/* 上一条消息发送成功后，立马推送一条消息 */
		cf.addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				if (future.isSuccess()){
					ctx.writeAndFlush(
							Message.MessageBase.newBuilder()
							.setClientId(msgBase.getClientId())
							.setCmd(Command.CommandType.PUSH_DATA)
							.setData("This is a push msg")
							.build()
							);
				}
			}
		});
		ReferenceCountUtil.release(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

	}

	@SuppressWarnings("deprecation")
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Attribute<String> attr = ctx.attr(clientInfo);
		String clientId = attr.get();
		System.out.println("Connection closed, client is " + clientId);
		cause.printStackTrace();
	}
}
