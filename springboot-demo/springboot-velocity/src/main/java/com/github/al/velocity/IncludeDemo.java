package com.github.al.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/4/14/014 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class IncludeDemo {

    public static void main(String[] args) throws Exception {
        // 初始化模板引擎
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        // 获取模板文件
        Template t = ve.getTemplate("templates/base.tl");
        // 设置变量
        VelocityContext ctx = new VelocityContext();
        ctx.put("param", "aa.sh");
        // 输出
        StringWriter sw = new StringWriter();
        t.merge(ctx,sw);
        System.out.println(sw.toString());

        //文件输出
        Path path = Paths.get("d:/workflow.xml");
        BufferedWriter writer =Files.newBufferedWriter(path);
        t.merge(ctx,writer);
        writer.close();
        System.out.println("输出成功！");
    }
}
