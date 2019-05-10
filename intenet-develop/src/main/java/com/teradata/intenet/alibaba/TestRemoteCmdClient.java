package com.teradata.intenet.alibaba;

import com.alibaba.pelican.chaos.client.RemoteCmd;
import com.alibaba.pelican.chaos.client.RemoteCmdClientConfig;
import com.alibaba.pelican.chaos.client.RemoteCmdResult;
import com.alibaba.pelican.chaos.client.impl.RemoteCmdClient;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/28/028 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class TestRemoteCmdClient {

    public static void main(String[] args) {
        //ECS可公网访问的IP
        String ip = "106.12.203.3";
        //ECS用户名，一般为root
        String userName = "root";
        //ECS登录密码
        String password = "123qwe!@#";

        //创建并初始化RemoteCmdClient实例
        RemoteCmdClientConfig remoteCmdClientConfig = new RemoteCmdClientConfig();
        remoteCmdClientConfig.setIp(ip);
        remoteCmdClientConfig.setUserName(userName);
        remoteCmdClientConfig.setPassword(password);
        RemoteCmdClient client = new RemoteCmdClient(remoteCmdClientConfig);

        //执行pwd命令
        RemoteCmdResult resultInfo = client.execCmdWithPTY(new RemoteCmd("jps"));

        System.out.println(resultInfo.getStdInfo());
    }
}
