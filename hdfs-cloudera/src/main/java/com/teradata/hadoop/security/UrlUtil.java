package com.teradata.hadoop.security;

public class UrlUtil {
	public static String toUrl(String sourceUrl){
		return sourceUrl.replaceAll(" ", "%20").replaceAll("/", "%2F");		
	}
	
	
	public static String replaceHostname2ip(String hadoopUrl){
		if(hadoopUrl.startsWith("http://")){
			String temp = hadoopUrl.substring(7);
			int index=temp.indexOf(":");
			String hostName;
			String other;
			if(index<0){
				hostName = temp;
				other="";
			}else{
				hostName = temp.substring(0, index);	
				other = temp.substring(index);
			}
			String ip = HostUtil.getIpByHostName(hostName);
			if(ip==null || ip.isEmpty()){
				ip=hostName;
			}
			return "http://"+ip+other;
		}else if(hadoopUrl.startsWith("https://")){
			String temp = hadoopUrl.substring(8);
			int index=temp.indexOf(":");
			String hostName;
			String other;
			if(index<0){
				hostName = temp;
				other="";
			}else{
				hostName = temp.substring(0, index);	
				other = temp.substring(index);
			}
			String ip = HostUtil.getIpByHostName(hostName);
			if(ip==null || ip.isEmpty()){
				ip=hostName;
			}
			return "https://"+ip+other;
		}
		
		return hadoopUrl;
	}
	
	public static void main(String[] agrs){
		String hadoopUrl = "http://e3basestorage3";
		System.out.println(replaceHostname2ip(hadoopUrl));
	}
}
