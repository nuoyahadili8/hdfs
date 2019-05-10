package com.teradata.intenet.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler  extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

			//do something msg
			ByteBuf buf = (ByteBuf)msg;
			byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			String request = new String(data, "utf-8");
			System.out.println("Server: " + request);
			//写给客户端
			String response = "我是反馈的信息";
			ctx.writeAndFlush(Unpooled.copiedBuffer("888".getBytes()));
			//.addListener(ChannelFutureListener.CLOSE);	//相应给客户端后，关闭客户端连接，由服务端关闭连接
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
