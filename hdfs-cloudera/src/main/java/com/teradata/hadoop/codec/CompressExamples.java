package com.teradata.hadoop.codec;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/11/19/019 Administrator Create 1.0
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
public class CompressExamples {

    private Configuration conf;

    private static String[] codec = {"org.apache.hadoop.io.compress.DeflateCodec","org.apache.hadoop.io.compress.GzipCodec",
                                     "org.apache.hadoop.io.compress.BZip2Codec","com.hadoop.compression.lzo.LzopCodec",
                                      "org.apache.hadoop.io.compress.Lz4Codec","org.apache.hadoop.io.compress.SnappyCodec"
                                    };

    public CompressExamples(){
        conf = new Configuration();
        conf.addResource("D:\\IdeaProjects\\hdfs\\hdfs-cloudera\\src\\main\\java\\com\\teradata\\hadoop\\codec\\my-core-site.xml");
    }

    /**
     * 使用指定的编解码器考查空间性能
     */
    private void compressSpaceInCodec(String codecClass){
        try{
            System.out.println("using=="+codecClass);
            //1、加载编解码器类  通过反射实例化编解对象
            Class clazz=Class.forName(codecClass);
            //2、实例化编解码器对象
            CompressionCodec codec= (CompressionCodec) ReflectionUtils.newInstance(clazz,conf);
            //3、获得编解码器，得到扩展名
            String codecName = clazz.getSimpleName();
            String ext = codecName.substring(0,codecName.length()-5).toLowerCase();
            //3、创建压缩输出流
            CompressionOutputStream out=codec.createOutputStream(new FileOutputStream("D:/codec/hadoop."+ext));

            IOUtils.copyBytes(new FileInputStream("d:/CDH-solr.pdf"),out,conf);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void testAllCompression(){
        CompressExamples ce=new CompressExamples();
        for (String code : codec){
            ce.compressSpaceInCodec(code);
        }
    }

    public static void main(String[] args) {
        testAllCompression();
    }
}
