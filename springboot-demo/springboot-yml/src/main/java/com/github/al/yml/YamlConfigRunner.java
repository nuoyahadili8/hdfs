package com.github.al.yml;

import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/15/015 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class YamlConfigRunner {

    public static final String propertiesFile="application-dev.yml";

    public static void main(String[] args) throws IOException {
//        String file=YamlConfigRunner.class.getClassLoader().getResource(propertiesFile).getFile();
//        Yaml yaml = new Yaml();
//        try( InputStream in = Files.newInputStream( Paths.get( file ) ) ) {
//            Configuration config = yaml.loadAs( in, Configuration.class );
//            System.out.println( config.toString() );
//        }

        Yaml yaml = new Yaml();
        String file=YamlConfigRunner.class.getClassLoader().getResource(propertiesFile).getFile();
        Object obj =yaml.load(new FileInputStream(file));

        Properties properties = yaml.loadAs(new FileInputStream(file), Properties.class );

        for (String key : properties.stringPropertyNames()) {
            System.out.println(key + "=" + properties.getProperty(key));
        }

//        System.out.println(obj);
//        //也可以将值转换为Map
//        Map map =(Map)yaml.load(new FileInputStream(file));
////        System.out.println(map);
//
//        for (Object key : map.keySet()) {
//            System.out.println(key+":\t"+map.get(key).toString());
//        }
    }
}
