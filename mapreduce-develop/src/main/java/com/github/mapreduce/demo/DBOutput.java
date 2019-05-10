package com.github.mapreduce.demo;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/26/026 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class DBOutput implements DBWritable, WritableComparable<DBOutput> {

    private String text;

    private int no;

    @Override
    public void readFields(ResultSet rs) throws SQLException {
        text = rs.getString("word");
        no = rs.getInt("count");
    }

    @Override
    public void write(PreparedStatement ps) throws SQLException {
        ps.setString(1, text);
        ps.setInt(2, no);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        text = input.readUTF();
        no = input.readInt();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeUTF(text);
        output.writeInt(no);
    }

    @Override
    public int compareTo(DBOutput o) {
        return text.compareTo(o.getText());
    }
}
