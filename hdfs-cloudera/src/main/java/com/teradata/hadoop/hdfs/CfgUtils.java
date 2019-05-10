package com.teradata.hadoop.hdfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CfgUtils {
	private static String cfgfile= "conf/config.properties";
	private static Properties props=null;
	
	public CfgUtils(){
		if(props==null){
			loadProperties(cfgfile);
		}
	}
	public String loadProperty(String configFile,String key) throws Exception{
		Properties props = loadProperties(configFile);
		return props.getProperty(key);
	}
	
	public String getProperty(String key){
		return props.getProperty(key);
	}
	
	private Properties loadProperties(String configFile){
		props = new Properties();
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(cfgfile);
		try {
			if(configFile.endsWith(".xml")){
				props.loadFromXML(stream);				
			}else{
				props.load(stream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props;
	}
	
	public static void main(String[] args){
		CfgUtils ut = new CfgUtils();
		System.out.println(ut.getProperty("THEME_PATH"));
	}

}
