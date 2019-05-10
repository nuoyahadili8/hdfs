package com.teradata.intenet.conllections;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/4/004 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class TreeMapDemo {

    public static void main(String[] args) {
        TreeMap<Object,Student> map=new TreeMap();
        Student s1=new Student("a1",3);
        Student s2=new Student("a2",30);
        Student s3=new Student("a3",23);
        Student s4=new Student("a4",13);
        Student s5=new Student("a5",2);
        Student s6=new Student("a6",10);
        map.put(s1,s1);
        map.put(s2,s2);
        map.put(s3,s3);
        map.put(s4,s4);
        map.put(s5,s5);
        map.put(s6,s6);

        for (Map.Entry<Object, Student> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().getScore());
        }

    }
}
