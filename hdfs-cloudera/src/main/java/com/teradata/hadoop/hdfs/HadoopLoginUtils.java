package com.teradata.hadoop.hdfs;

import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.HADOOP_SECURITY_AUTHORIZATION;
import java.io.IOException;
import java.security.PrivilegedExceptionAction;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.log4j.Logger;

public class HadoopLoginUtils {
	private static Logger log = Logger.getLogger(HadoopLoginUtils.class);

	private static final String JAVA_SECURITY_KRB5_CONF_KEY = "java.security.krb5.conf";

	private static String HADOOP_CORE_SITE_XML = "hadoop.core.site.xml";
	private static String HADOOP_HDFS_SITE_XML = "hadoop.hdfs.site.xml";

	public static final String REALM_NAME = "kerberos.principal.realm";
	public static final String KERBEROS_CONF_PATH = "kerberos.conf.path";
	public static final String HADOOP_SECURITY_KERBEROS = "kerberos";
	public static final String USERNAME_CLIENT_KEYTAB_FILE = "username.client.keytab.file";
	public static final String USERNAME_CLIENT_KEYTAB_PRINCIPAL = "username.client.kerberos.principal";
	public static final String KEYTAB_FILE_KEY = "hdfs.keytab.file";
	public static final String USER_NAME_KEY = "hdfs.kerberos.principal";

	private static String hadoopCoreXml;
	private static String hadoopHdfsXml;

	private static String kerberosconfpath;
	private static String PRINCIPAL_REALM;

	private static final String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = "hbase.zookeeper.property.clientPort";
	private static final String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";

	private static final String HBASE_MASTER = "hbase.master";
	private static final String HADOOP_USER_NAME = "hbase.hadoop.user";

	private static String HBASE_SITE_XML = "hbase.site.xml";

	private static final String HADOOP_SECURITY_AUTHENTICATION = "hadoop.security.authentication";
	private static final String HBASE_SECURITY_AUTHENTICATION = "hbase.security.authentication";
	private static final String HBASE_SECURITY_AUTHORIZATION = "hbase.security.authorization";

	private static final String HBASE_MASTER_KERBEROS_PRINCIPAL = "hbase.master.kerberos.principal";
	private static final String HBASE_REGIONSERVER_KERBEROS_PRINCIPAL = "hbase.regionserver.kerberos.principal";

	private static final String HBASE_SECURITY_KERBEROS = "kerberos";

	private static String hbaseXml;

	private static String masterKerberosPrincipal;
	private static String regionserverKerberosPrincipal;

	private static String zookeeperPort;
	private static String zookeeperQuorum;
	private static String hbaseMaster;

	static {
		CfgUtils cfg = new CfgUtils();
		hadoopCoreXml = cfg.getProperty(HADOOP_CORE_SITE_XML);
		hadoopHdfsXml = cfg.getProperty(HADOOP_HDFS_SITE_XML);

		kerberosconfpath = cfg.getProperty(KERBEROS_CONF_PATH);
		PRINCIPAL_REALM = cfg.getProperty(REALM_NAME);

		hbaseXml = cfg.getProperty(HBASE_SITE_XML);

		zookeeperPort = cfg.getProperty(HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT);
		zookeeperQuorum = cfg.getProperty(HBASE_ZOOKEEPER_QUORUM);
		hbaseMaster = cfg.getProperty(HBASE_MASTER);

		masterKerberosPrincipal = cfg.getProperty(HBASE_MASTER_KERBEROS_PRINCIPAL);
		regionserverKerberosPrincipal = cfg.getProperty(HBASE_REGIONSERVER_KERBEROS_PRINCIPAL);
	}

	public HadoopLoginUtils() {

	}

	public Configuration loginHdfs(String oozieUser, String keyfile) {
		log.info("loginHadoop start............oozieUser:" + oozieUser);

		Configuration conf = new Configuration();
		conf.addResource(new Path(hadoopCoreXml));
		conf.addResource(new Path(hadoopHdfsXml));
		conf.setBoolean("fs.hdfs.impl.disable.cache", true);
		if (HADOOP_SECURITY_KERBEROS.equalsIgnoreCase(conf.get(HADOOP_SECURITY_AUTHENTICATION))) {
			System.setProperty(JAVA_SECURITY_KRB5_CONF_KEY, kerberosconfpath);

			String oozieUser_princ = oozieUser + PRINCIPAL_REALM;
			conf.set(HADOOP_SECURITY_AUTHENTICATION, "kerberos");
			conf.set(HADOOP_SECURITY_AUTHORIZATION, "true");
			conf.set(USERNAME_CLIENT_KEYTAB_FILE, keyfile);
			conf.set(USERNAME_CLIENT_KEYTAB_PRINCIPAL, oozieUser_princ);
//			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
			conf.set("fs.default.name","hdfs://nameservice1");
			// UserGroupInformation.setLoginUser(null);
			// 使用设置的用户登陆
			UserGroupInformation.setConfiguration(conf);
			try {
				log.info("before loginUserFromKeytab............oozieUser:" + oozieUser);
				UserGroupInformation.loginUserFromKeytab(oozieUser_princ, keyfile);
				log.info("after loginUserFromKeytab............oozieUser:" + oozieUser);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.setProperty("HADOOP_USER_NAME", oozieUser);
			System.setProperty("user.name", oozieUser);
			conf.set("hadoop.user.name", oozieUser);
			conf.set("user.name", oozieUser);
		}

		return conf;
	}

	public static Configuration loginHbase(String oozieUser, String keyfile) {
		Configuration hbaseConfig = HBaseConfiguration.create();
		hbaseConfig.addResource(new Path(hbaseXml));
		hbaseConfig.set(HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT, zookeeperPort);
		hbaseConfig.set(HBASE_ZOOKEEPER_QUORUM, zookeeperQuorum);
		hbaseConfig.set(HBASE_MASTER, hbaseMaster);

		try {
			if (HBASE_SECURITY_KERBEROS.equalsIgnoreCase(hbaseConfig.get(HBASE_SECURITY_AUTHENTICATION)) && "true".equalsIgnoreCase(hbaseConfig.get(HBASE_SECURITY_AUTHORIZATION))) {
				// 设置安全验证方式为kerberos
				hbaseConfig.set(HADOOP_SECURITY_AUTHENTICATION, "kerberos");
				hbaseConfig.set(HBASE_SECURITY_AUTHENTICATION, "kerberos");
				// 设置hbase master及hbase regionserver的安全标识，这两个值可以在hbase-site.xml中找到
				hbaseConfig.set(HBASE_MASTER_KERBEROS_PRINCIPAL, masterKerberosPrincipal);
				hbaseConfig.set(HBASE_REGIONSERVER_KERBEROS_PRINCIPAL, regionserverKerberosPrincipal);

				String oozieUser_princ = oozieUser + PRINCIPAL_REALM;
				// UserGroupInformation.setLoginUser(null);
				// 使用设置的用户登陆
				UserGroupInformation.setConfiguration(hbaseConfig);
				UserGroupInformation.loginUserFromKeytab(oozieUser_princ, keyfile);

			} else {
				hbaseConfig.set(HADOOP_USER_NAME, oozieUser);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hbaseConfig;
	}
}
