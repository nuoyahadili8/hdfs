package com.github.avro;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

/**
 * @Title AvroSerializerTest.java
 * @Description 使用 avro 对 com.avro.example.User 类的对象进行序列化
 * @Author http://www.trieuvan.com/apache/avro/avro-1.8.2/java/
 * @Date 2018-06-21 15:42:02
 */
public class AvroSerializerTest {

    public static void main(String[] args) throws IOException {

        User user1 = new User();
        user1.setName("Tom");
        user1.setFavoriteNumber(7);

        User user2 = new User("Jack", 15, "red");

        User user3 = User.newBuilder()
                .setName("Harry")
                .setFavoriteNumber(1)
                .setFavoriteColor("green")
                .build();

        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
        DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
        dataFileWriter.create(user1.getSchema(), new File("users.avro"));
        dataFileWriter.append(user1);
        dataFileWriter.append(user2);
        dataFileWriter.append(user3);
        dataFileWriter.close();

    }

}