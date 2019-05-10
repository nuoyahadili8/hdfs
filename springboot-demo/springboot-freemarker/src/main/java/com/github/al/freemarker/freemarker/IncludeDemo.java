package com.github.al.freemarker.freemarker;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/4/13/013 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class IncludeDemo {


    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDirectoryForTemplateLoading(new File("D:/IdeaProjects/hdfs/springboot-demo/springboot-freemarker/src/main/resources/template"));
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
        //这个一定要设置，不然在生成的页面中 会乱码
        configuration.setDefaultEncoding("UTF-8");
        //获取或创建一个模版。
        Template template = configuration.getTemplate("base.tl");
        Map<String, Object> paramMap = new HashMap();

        paramMap.put("param","aa.sh");

        Writer writer  = new OutputStreamWriter(new FileOutputStream("D:/IdeaProjects/hdfs/springboot-demo/springboot-freemarker/src/main/resources/template/workflow.xml"),"UTF-8");
        template.process(paramMap, writer);

        System.out.println("恭喜，生成成功~~");
    }
}
