package com.github.mapreduce.bean;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-06-20 9:32
 */
public class User implements DBWritable,WritableComparable<User> {

    private Integer userId;
    private String userName;
    private Integer userAge;



    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    @Override
    public String toString() {
        return  userId +
                "\t" + userName +
                "\t" +userAge;
    }

    public User() {
    }

    public User(Integer userId, String userName, Integer userAge) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1,userId);
        preparedStatement.setString(2,userName);
        preparedStatement.setInt(3,userAge);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        userId=resultSet.getInt(1);
        userName=resultSet.getString(2);
        userAge=resultSet.getInt(3);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(userId);
        dataOutput.writeUTF(userName);
        dataOutput.writeInt(userAge);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        userId=dataInput.readInt();
        userName=dataInput.readUTF();
        userAge=dataInput.readInt();
    }


    @Override
    public int compareTo(User o) {
        return this.userId.compareTo(o.getUserId());
    }

}
