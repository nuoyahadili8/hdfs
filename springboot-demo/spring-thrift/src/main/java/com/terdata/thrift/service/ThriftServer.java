package com.terdata.thrift.service;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/11/26/026 Administrator Create 1.0
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
import com.terdata.thrift.controller.JazzRpcController;
import com.terdata.thrift.iface.JazzService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ThriftServer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${thrift.port}")
    private int port;
    @Value("${thrift.minWorkerThreads}")
    private int minThreads;
    @Value("${thrift.maxWorkerThreads}")
    private int maxThreads;

    private TBinaryProtocol.Factory protocolFactory;
    private TTransportFactory transportFactory;

    @Autowired
    private JazzRpcController jazzRpcController;

    public void init() {
        protocolFactory = new TBinaryProtocol.Factory();
        transportFactory = new TTransportFactory();
    }

    public void start() {
        //TMultiplexedProcessor processor = new TMultiplexedProcessor();
        //processor.registerProcessor(JazzService.class.getSimpleName(), new JazzService.Processor<JazzService.Iface>(hadoopService));
        JazzService.Processor processor = new JazzService.Processor<JazzService.Iface>(jazzRpcController);
        init();
        try {
            TServerTransport transport = new TServerSocket(port);
            TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(transport);
            tArgs.processor(processor);
            tArgs.protocolFactory(protocolFactory);
            tArgs.transportFactory(transportFactory);
            tArgs.minWorkerThreads(minThreads);
            tArgs.maxWorkerThreads(maxThreads);
            TServer server = new TThreadPoolServer(tArgs);
            //TServer server = new TSimpleServer(tArgs);
            logger.info("thrift服务启动成功, 端口={}", port);
            server.serve();
        } catch (Exception e) {
            logger.error("thrift服务启动失败", e);
        }

    }
}
