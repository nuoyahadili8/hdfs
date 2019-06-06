package com.teradata.hadoop.security;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUtil {
    public static String getIpByHostName(String hostName){
    	if(hostName==null){
			return null;
		}
    	 try {
			InetAddress address = InetAddress.getByName(hostName);
			return address.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "";
		}
    }
}
