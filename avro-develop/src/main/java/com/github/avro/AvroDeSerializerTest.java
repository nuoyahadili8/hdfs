package com.github.avro;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

/**
 * @Title AvroDeSerializerTest.java
 * @Description 解析 avro 序列化后的对象
 * @Author http://www.trieuvan.com/apache/avro/avro-1.8.2/java/
 * @Date 2018-06-21 15:58:10
 */
public class AvroDeSerializerTest {

    public static void main(String[] args) throws IOException {

        DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
        DataFileReader<User> dataFileReader = new DataFileReader<User>(new File("users.avro"), userDatumReader);
        User user = null;
        while (dataFileReader.hasNext()) {
            user = dataFileReader.next(user);
            System.out.println(user);
        }
    }
}
