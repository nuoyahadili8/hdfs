package com.github.al.yml;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/15/015 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class Yml2Properties {

    private String encoding="utf8";

    private String propertiesFile="application-dev.yml";


    public List<String> Yml2PropertiesMethod(){
        final String DOT = ".";
        List<String> lines = new LinkedList<>();
        String yaml=YamlConfigRunner.class.getClassLoader().getResource(propertiesFile).getFile();
        try {
            YAMLFactory yamlFactory = new YAMLFactory();
            YAMLParser parser = yamlFactory.createParser(
                    new InputStreamReader(new FileInputStream(yaml), Charset.forName(this.encoding)));

            String key = "";
            String value = null;
            JsonToken token = parser.nextToken();
            while (token != null) {
                if (JsonToken.START_OBJECT.equals(token)) {
                    // do nothing
                } else if (JsonToken.FIELD_NAME.equals(token)) {
                    if (key.length() > 0) {
                        key = key + DOT;
                    }
                    key = key + parser.getCurrentName();

                    token = parser.nextToken();
                    if (JsonToken.START_OBJECT.equals(token)) {
                        continue;
                    }
                    value = parser.getText();
                    lines.add(key + "=" + value);

                    int dotOffset = key.lastIndexOf(DOT);
                    if (dotOffset > 0) {
                        key = key.substring(0, dotOffset);
                    }
                    value = null;
                } else if (JsonToken.END_OBJECT.equals(token)) {
                    int dotOffset = key.lastIndexOf(DOT);
                    if (dotOffset > 0) {
                        key = key.substring(0, dotOffset);
                    } else {
                        key = "";
                        lines.add("");
                    }
                }
                token = parser.nextToken();
            }
            parser.close();

            for (String line:lines){
                System.out.println(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    public static void main(String[] args) {
        Yml2Properties tools=new Yml2Properties();
        tools.Yml2PropertiesMethod();
    }
}
