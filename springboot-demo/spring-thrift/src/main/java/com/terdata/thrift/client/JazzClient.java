package com.terdata.thrift.client;

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
import com.terdata.thrift.iface.JazzService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

public class JazzClient {
    private  JazzService.Client jazzService;
    private TBinaryProtocol protocol;
    private TSocket transport;
    private String host;
    private int port;
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public void init() {
        transport = new TSocket(host, port);
        protocol = new TBinaryProtocol(transport);
        jazzService = new JazzService.Client(protocol);
    }

    public JazzService.Client getJazzService() {
        return jazzService;
    }

    public  void  open() throws TTransportException {
        transport.open();
    }

    public  void  close()
    {
        transport.close();
    }

}
