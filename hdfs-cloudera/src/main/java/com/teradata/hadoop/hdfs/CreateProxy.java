package com.teradata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/22/022 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class CreateProxy {

    public static void main(String[] args) throws IOException, InterruptedException {
        HadoopLoginUtils login=new HadoopLoginUtils();
        final Configuration conf=login.loginHdfs("oozieweb", "D:/profile/keytab/2/oozieweb.keytab");
        UserGroupInformation ugi = UserGroupInformation.createProxyUser("hadoop1", UserGroupInformation.getLoginUser());
        ugi.doAs(new PrivilegedExceptionAction<Void>() {
            @Override
            public Void run() throws Exception {
                // Submit a job
                FileSystem fs = FileSystem.get(conf);
                fs.listStatus(new Path("/user/hadoop1"));
//                Path sourcePath = new Path("/user/hadoop1/abk1");
//                fs.mkdirs(sourcePath);
                return null;
            }
        });
    }
}
