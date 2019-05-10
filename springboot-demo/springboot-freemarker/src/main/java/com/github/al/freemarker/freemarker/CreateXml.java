package com.github.al.freemarker.freemarker;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;

import freemarker.template.*;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/4/13/013 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class CreateXml {
    public static void main(String[] args) {  
        try {
            //创建一个合适的Configration对象
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setDirectoryForTemplateLoading(new File("D:/IdeaProjects/hdfs/springboot-demo/springboot-freemarker/src/main/resources/template"));
            configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
            //这个一定要设置，不然在生成的页面中 会乱码
            configuration.setDefaultEncoding("UTF-8");
            //获取或创建一个模版。
            Template template = configuration.getTemplate("workflow_tmp.xml");
            Map<String, Object> paramMap = new HashMap();

            paramMap.put("jobName","antest");
            paramMap.put("nameNode","${nameNode}");
            paramMap.put("jobTracker","${jobTracker}");
            paramMap.put("queueName","cdrapp");
            paramMap.put("error","${wf:errorMessage(wf:lastErrorNode())}");

            List<String> params=new ArrayList<>();

            params.add("<param>TX_DATE=${TX_DATE}</param>");
            params.add("<param>TX_MONTH=${TX_MONTH}</param>");

            paramMap.put("params",params);

            Writer writer  = new OutputStreamWriter(new FileOutputStream("D:/IdeaProjects/hdfs/springboot-demo/springboot-freemarker/src/main/resources/template/workflow.xml"),"UTF-8");
            template.process(paramMap, writer);

            System.out.println("恭喜，生成成功~~");
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }
}
