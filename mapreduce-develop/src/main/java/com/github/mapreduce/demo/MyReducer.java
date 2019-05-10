package com.github.mapreduce.demo;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/26/026 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class MyReducer extends Reducer<DBOutput, IntWritable, DBOutput, IntWritable> {

    @Override
    public void reduce(DBOutput key, Iterable<IntWritable> values, Reducer<DBOutput, IntWritable, DBOutput, IntWritable>.Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        DBOutput dbKey = new DBOutput();

        for (IntWritable val : values) {
            sum += val.get();
        }
        dbKey.setText(key.getText());
        dbKey.setNo(sum);
        context.write(dbKey, new IntWritable(sum));
    }
}
