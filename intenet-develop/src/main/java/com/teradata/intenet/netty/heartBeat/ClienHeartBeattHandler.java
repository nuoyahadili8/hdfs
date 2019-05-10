package com.teradata.intenet.netty.heartBeat;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
public class ClienHeartBeattHandler extends ChannelInboundHandlerAdapter {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> heartBeat;
    private InetAddress addr;
    private static final String SUCCESS_KEY = "auth_success_key";

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        this.addr = InetAddress.getLocalHost();
        String ip = this.addr.getHostAddress();
        String key = "1234";
        String auth = ip + "," + key;
        ctx.writeAndFlush(auth);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof String){
                String ret = (String) msg;
                if (SUCCESS_KEY.equals(ret)){
                    //握手成功，主动发送心跳信息
                    this.heartBeat = this.scheduler.scheduleWithFixedDelay(new HeartBeatTask(ctx), 0L, 2L, TimeUnit.SECONDS);
                    System.out.println(msg);
                } else {
                    System.out.println(msg);
                }
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        public void run() {
            try {
                RequestInfo info = new RequestInfo();

                info.setIp(ClienHeartBeattHandler.this.addr.getHostAddress());
                Sigar sigar = new Sigar();

                CpuPerc cpuPerc = sigar.getCpuPerc();
                HashMap cpuPercMap = new HashMap();
                cpuPercMap.put("combined", Double.valueOf(cpuPerc.getCombined()));
                cpuPercMap.put("user", Double.valueOf(cpuPerc.getUser()));
                cpuPercMap.put("sys", Double.valueOf(cpuPerc.getSys()));
                cpuPercMap.put("wait", Double.valueOf(cpuPerc.getWait()));
                cpuPercMap.put("idle", Double.valueOf(cpuPerc.getIdle()));

                Mem mem = sigar.getMem();
                HashMap memoryMap = new HashMap();
                memoryMap.put("total", Long.valueOf(mem.getTotal() / 1024L));
                memoryMap.put("used", Long.valueOf(mem.getUsed() / 1024L));
                memoryMap.put("free", Long.valueOf(mem.getFree() / 1024L));
                info.setCpuPercMap(cpuPercMap);
                info.setMemoryMap(memoryMap);
                this.ctx.writeAndFlush(info);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            if (ClienHeartBeattHandler.this.heartBeat != null) {
                ClienHeartBeattHandler.this.heartBeat.cancel(true);
                ClienHeartBeattHandler.this.heartBeat = null;
            }
            ctx.fireExceptionCaught(cause);
        }
    }
}
