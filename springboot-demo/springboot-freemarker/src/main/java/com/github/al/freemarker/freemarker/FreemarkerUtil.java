package com.github.al.freemarker.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.IOException;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/4/13/013 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class FreemarkerUtil {

    public Template getTemplate(String name) {
        try {
            // 通过Freemaker的Configuration读取相应的ftl
            Configuration cfg = new Configuration(new Version("1"));
            // 设定去哪里读取相应的ftl模板文件
            cfg.setClassForTemplateLoading(this.getClass(), "/ftl");
            // 在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate(name);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
