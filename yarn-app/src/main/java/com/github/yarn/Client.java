package com.github.yarn;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.ApplicationConstants;
import org.apache.hadoop.yarn.api.records.*;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.client.api.YarnClientApplication;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.util.Records;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class Client {

	private YarnConfiguration conf;

	public static void main(String[] args){
		System.out.println("Client: Initializing");
		try {
			new Client().run();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void run() throws Exception {
		conf = new YarnConfiguration();
		conf.addResource(new Path("file:///D:\\hadoop_conf\\s1\\core-site.xml"));
		conf.addResource(new Path("file:///D:\\hadoop_conf\\s1\\hdfs-site.xml"));
		conf.addResource(new Path("file:///D:\\hadoop_conf\\s1\\yarn-site.xml"));

		// Create Yarn Client
		YarnClient client = YarnClient.createYarnClient();
		client.init(conf);
		client.start();

		// Create Application
		YarnClientApplication app = client.createApplication();

		// Create AM Container
		ContainerLaunchContext amCLC = Records.newRecord(ContainerLaunchContext.class);
		amCLC.setCommands(Collections.singletonList("$JAVA_HOME/bin/java"
				+ " -Xmx256M"
				+ " com.github.yarn.client.AppMaster"
				+ " 1>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stdout"
				+ " 2>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stderr"));

		// Set AM jar
		LocalResource jar = Records.newRecord(LocalResource.class);
		Utils.setUpLocalResource(Utils.YARNAPP_JAR_PATH, jar, conf);
		amCLC.setLocalResources(Collections.singletonMap(Utils.YARNAPP_JAR_NAME, jar));

		// Set AM CLASSPATH
		Map<String, String> env = new HashMap<String, String>();
		Utils.setUpEnv(env, conf);
		amCLC.setEnvironment(env);

		// Set AM resources
		Resource res = Records.newRecord(Resource.class);
		res.setMemory(256);
		res.setVirtualCores(1);

		// Create ApplicationSubmissionContext
		ApplicationSubmissionContext appContext = app.getApplicationSubmissionContext();
		appContext.setApplicationName("YARN-APP");
		appContext.setQueue("default");
		appContext.setAMContainerSpec(amCLC);
		appContext.setResource(res);
		appContext.setApplicationType("spark");

		// Submit Application
		ApplicationId id = appContext.getApplicationId();
		System.out.println("Client: Submitting " + id);
		client.submitApplication(appContext);

		ApplicationReport appReport = client.getApplicationReport(id);
		YarnApplicationState appState = appReport.getYarnApplicationState();
		while (appState != YarnApplicationState.FINISHED
				&& appState != YarnApplicationState.KILLED
				&& appState != YarnApplicationState.FAILED) {
			Thread.sleep(1000);
			appReport = client.getApplicationReport(id);
			appState = appReport.getYarnApplicationState();
		}

		System.out.println("Client: Finished " + id + " with state " + appState);
	}

}
