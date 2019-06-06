package com.teradata.hadoop.yarn;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.security.authentication.client.AuthenticationException;

public class YarnMain {
	private static KerberosWebYarnConnection conn = null;
	
	public static void main(String[] args) throws IOException, AuthenticationException{
		init();
		System.out.println("apps:" + conn.getApps());
	}
	
	/**
	 * 完成认证，获取连接实例
	 */
	public static void init(){
      //主RM的IP和端口
	  //String url = "https://8-5-214-7:26001";
      String url = "https://host189-132-134-121:26001";
	  String userdir = System.getProperty("user.dir") + File.separator + "conf" + File.separator;
      String userName = "tester1";
      String userKeytabFile = userdir + "user.keytab";
      String krb5File = userdir + "krb5.conf";
      conn = new KerberosWebYarnConnection(url, krb5File, userKeytabFile, userName);
	}
}
