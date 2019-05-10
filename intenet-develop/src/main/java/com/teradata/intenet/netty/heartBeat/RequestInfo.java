package com.teradata.intenet.netty.heartBeat;

import java.io.Serializable;
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
public class RequestInfo implements Serializable {

    private String ip;
    private HashMap<String, Object> cpuPercMap;
    private HashMap<String, Object> memoryMap;

    public String getIp()
    {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public HashMap<String, Object> getCpuPercMap() {
        return this.cpuPercMap;
    }
    public void setCpuPercMap(HashMap<String, Object> cpuPercMap) {
        this.cpuPercMap = cpuPercMap;
    }
    public HashMap<String, Object> getMemoryMap() {
        return this.memoryMap;
    }
    public void setMemoryMap(HashMap<String, Object> memoryMap) {
        this.memoryMap = memoryMap;
    }
}
