package com.teradata.spark2x;

import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/11/27/027 Administrator Create 1.0
 * @Copyright ©2017-2017
 * 声明：本项目是关于Hive、HBase的个人爱好代码。与
 * !@#$**中!@#$**央!@#$**国!@#$**债!@#$**、
 * !@#$**中!@#$**共!@#$**中!@#$**央!@#$**、
 * !@#$**公!@#$**安!@#$**部!@#$**、
 * !@#$**中!@#$**央!@#$**结!@#$**算!@#$**、
 * !@#$**t!@#$**e!@#$**r!@#$**a!@#$**d
 * !@#$**a!@#$**t!@#$**a!@#$**无半点关系，特此声明！
 * @Modified By:
 */
public class SparkJavaDemo {

    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local[*]")
                .appName("hello-wrold")
//                .config("spark.some.config.option", "some-value")
                .getOrCreate();
        List<Person> persons = new ArrayList<>();

        persons.add(new Person("zhangsan", 22, "male"));
        persons.add(new Person("lisi", 25, "male"));
        persons.add(new Person("wangwu", 23, "female"));

        spark.createDataFrame(persons, Person.class).show();
        spark.close();
    }
}
