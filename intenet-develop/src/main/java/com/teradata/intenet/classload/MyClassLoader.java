package com.teradata.intenet.classload;

import java.io.*;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/18/018 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class MyClassLoader extends ClassLoader {

    private String path = "D:/IdeaProjects/hdfs/intenet-develop/target/classes/";

    protected MyClassLoader(String path) {
        this.path = path;
    }

    protected MyClassLoader(ClassLoader parent, String path) {
        super(parent);
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = loadClassData(name);
        return defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) {
        try {
            name = name.replace(".", "/");
            FileInputStream fis = new FileInputStream(new File(path + name + ".class"));
//            ByteArrayInputStream bis = new ByteArrayInputStream(fis);

            ByteArrayOutputStream bab = new ByteArrayOutputStream();
            byte[] bytes = new byte[2048];
            int n = -1;
            while ((n = fis.read(bytes, 0, bytes.length)) != -1) {
                bab.write(bytes);
            }
            return bab.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
