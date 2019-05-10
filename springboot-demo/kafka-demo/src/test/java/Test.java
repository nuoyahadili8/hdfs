import org.springframework.beans.factory.config.YamlProcessor;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/15/015 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class Test{

    public static final String propertiesFile="config/atomic-auth-dev.yml";

    public static void main(String[] args) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        String file=Test.class.getClassLoader().getResource(propertiesFile).getFile();
        Object obj =yaml.load(new FileInputStream(file));

        System.out.println(obj);
        //也可以将值转换为Map
        Map map =(Map)yaml.load(new FileInputStream(file));
//        System.out.println(map);

        for (Object key : map.keySet()) {
            System.out.println(key+":\t"+map.get(key).toString());
        }

    }
}
