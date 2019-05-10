import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/15/015 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
@SpringBootApplication
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:config/demo.yml")
public class YamlPropertysourceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx =
                SpringApplication.run(YamlPropertysourceApplication.class, args);

        ConfigurableEnvironment env = ctx.getEnvironment();
        env.getPropertySources()
                .forEach(ps -> System.out.println(ps.getName() + " : " + ps.getClass()));

        System.out.println("Value of `server.port` = " + env.getProperty("foo.baz"));
    }
}
