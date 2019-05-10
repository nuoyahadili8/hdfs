package com.teradata.intenet.netty.heartBeat;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashMap;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/11/20/020 Administrator Create 1.0
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
public class ServerHeartBeatHandler extends ChannelInboundHandlerAdapter {
    private static HashMap<String, String> AUTH_IP_MAP = new HashMap();
    private static final String SUCCESS_KEY = "auth_success_key";

    static {
        AUTH_IP_MAP.put("169.254.245.214", "1234");
    }

    private boolean auth(ChannelHandlerContext ctx, Object msg) {
        String[] ret = ((String) msg).split(",");
        String auth = (String) AUTH_IP_MAP.get(ret[0]);
        if ((auth != null) && (auth.equals(ret[1]))) {
            ctx.writeAndFlush("auth_success_key");
            return true;
        }
        ctx.writeAndFlush("auth failure !").addListener(ChannelFutureListener.CLOSE);
        return false;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof String) {
            auth(ctx, msg);
        } else if (msg instanceof RequestInfo) {
            RequestInfo info = (RequestInfo) msg;
            System.out.println("--------------------------------------------");
            System.out.println("当前主机ip为: " + info.getIp());
            System.out.println("当前主机cpu情况: ");
            HashMap cpu = info.getCpuPercMap();
            System.out.println("总使用率: " + cpu.get("combined"));
            System.out.println("用户使用率: " + cpu.get("user"));
            System.out.println("系统使用率: " + cpu.get("sys"));
            System.out.println("等待率: " + cpu.get("wait"));
            System.out.println("空闲率: " + cpu.get("idle"));

            System.out.println("当前主机memory情况: ");
            HashMap memory = info.getMemoryMap();
            System.out.println("内存总量: " + memory.get("total"));
            System.out.println("当前内存使用量: " + memory.get("used"));
            System.out.println("当前内存剩余量: " + memory.get("free"));
            System.out.println("--------------------------------------------");

            ctx.writeAndFlush("info received!");
        } else {
            ctx.writeAndFlush("connect failure!").addListener(ChannelFutureListener.CLOSE);
        }
    }
}
