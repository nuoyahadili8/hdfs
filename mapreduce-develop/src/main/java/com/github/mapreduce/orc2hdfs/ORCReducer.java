package com.github.mapreduce.orc2hdfs;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.orc.TypeDescription;
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
public class ORCReducer extends Reducer<Text, Text, NullWritable, OrcStruct> {

    private TypeDescription schema = TypeDescription.fromString("struct<name:string,mobile:string>");
    private OrcStruct pair = (OrcStruct) OrcStruct.createValue(schema);

    private final NullWritable nw = NullWritable.get();

    @Override
    public void reduce(Text key, Iterable<Text> values, Context output)
            throws IOException, InterruptedException {
        for (Text val : values) {
            pair.setFieldValue(0, key);
            pair.setFieldValue(1, val);
            output.write(nw, pair);
        }
    }

}
