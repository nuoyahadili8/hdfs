package com.teradata.netty.server.handler;


import com.teradata.netty.server.common.protobuf.Command;
import com.teradata.netty.server.common.protobuf.Message;
import com.teradata.netty.server.config.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

/**
 * 连接认证Handler
 * 1. 连接成功后客户端发送CommandType.AUTH指令，Sever端验证通过后返回CommandType.AUTH_BACK指令
 * 2. 处理心跳指令
 * 3. 触发下一个Handler
 * @author Ke Shanqiang
 *
 */
@Component
@Qualifier("authServerHandler")
@ChannelHandler.Sharable
public class AuthServerHandler extends ChannelInboundHandlerAdapter {
	private final AttributeKey<String> clientInfo = AttributeKey.valueOf("clientInfo");
	
	@Autowired
	@Qualifier("channelRepository")
	ChannelRepository channelRepository;
	
	@SuppressWarnings("deprecation")
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message.MessageBase msgBase = (Message.MessageBase)msg;
		String clientId = msgBase.getClientId();
		
		Channel ch = channelRepository.get(clientId);
		if(null == ch){
			ch = ctx.channel();
			channelRepository.put(clientId, ch);
		}
		/*认证处理*/
		if(msgBase.getCmd().equals(Command.CommandType.AUTH)){
			System.out.println("我是验证处理逻辑");
			Attribute<String> attr = ctx.attr(clientInfo);
			attr.set(clientId);
			channelRepository.put(clientId, ctx.channel());
			
			ctx.writeAndFlush(createData(clientId, Command.CommandType.AUTH_BACK, "This is response data").build());
		
		}else if(msgBase.getCmd().equals(Command.CommandType.PING)){
			//处理ping消息
			ctx.writeAndFlush(createData(clientId, Command.CommandType.PONG, "This is pong data").build());
		
		}else{
			if(ch.isOpen()){
				//触发下一个handler
				ctx.fireChannelRead(msg);
				System.out.println("我进业务入处理逻辑");
			}
		}
		ReferenceCountUtil.release(msg);
	}
	private Message.MessageBase.Builder createData(String clientId, Command.CommandType cmd, String data){
		Message.MessageBase.Builder msg = Message.MessageBase.newBuilder();
		msg.setClientId(clientId);
		msg.setCmd(cmd);
		msg.setData(data);
		return msg;
	}
}
