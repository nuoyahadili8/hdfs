package com.github.mapreduce.orc2hdfs;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.orc.mapred.OrcStruct;

import java.io.IOException;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/2/17/017 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class ORCMapper extends Mapper<NullWritable, OrcStruct, Text, Text> {
    @Override
    public void map(NullWritable key, OrcStruct value, Context output) throws IOException, InterruptedException {
        output.write((Text) value.getFieldValue(1), (Text) value.getFieldValue(2));
    }
}
