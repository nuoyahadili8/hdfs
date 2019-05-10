package com.teradata.netty.client.handler;


import com.teradata.netty.server.common.protobuf.Command;
import com.teradata.netty.server.common.protobuf.Message;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogicClientHandler extends SimpleChannelInboundHandler<Message.MessageBase>{

	private final static String CLIENTID = "123456789";

	// 连接成功后，向server发送消息  
	@Override  
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Message.MessageBase.Builder authMsg = Message.MessageBase.newBuilder();
		authMsg.setClientId(CLIENTID);
		authMsg.setCmd(Command.CommandType.AUTH);
		authMsg.setData("This is auth data");

		ctx.writeAndFlush(authMsg.build());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("连接断开 ");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message.MessageBase msg) throws Exception {
		if(msg.getCmd().equals(Command.CommandType.AUTH_BACK)){
			System.out.println("验证成功");
			ctx.writeAndFlush(
					Message.MessageBase.newBuilder()
					.setClientId(CLIENTID)
					.setCmd(Command.CommandType.PUSH_DATA)
					.setData("This is upload data")
					.build()
					);

		}else if(msg.getCmd().equals(Command.CommandType.PING)){
			//接收到server发送的ping指令
			System.out.println(msg.getData());
			
		}else if(msg.getCmd().equals(Command.CommandType.PONG)){
			//接收到server发送的pong指令
			System.out.println(msg.getData());
			
		}else if(msg.getCmd().equals(Command.CommandType.PUSH_DATA)){
			//接收到server推送数据
			System.out.println(msg.getData());
			
		}else if(msg.getCmd().equals(Command.CommandType.PUSH_DATA_BACK)){
			//接收到server返回数据
			System.out.println(msg.getData());
			
		}else{
			System.out.println(msg.getData());
		}
	}
}
