package com.teradata.intenet.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException: BufferedInputStream、BufferedOutputStream（缓存字节流）使用方式和字节流差不多，但是效率更高（推荐使用）
 * @Author: <li>2019/3/27/027 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class BufferedStreamDemo {

    public static void main(String[] args) throws Exception{
        //读取文件(缓存字节流)
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("d:\\user_phone_no.txt"));
        //写入相应的文件
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("d:\\2.txt"));
        //读取数据
        //一次性取多少字节
        byte[] bytes = new byte[2048];
        //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
        int n = -1;
        //循环取出数据
        while ((n = in.read(bytes,0,bytes.length)) != -1) {
            //转换成字符串
            String str = new String(bytes,0,n,"GBK");
//            System.out.println(str);
            //写入相关文件
            out.write(bytes, 0, n);
        }
        //清楚缓存
        out.flush();
        //关闭流
        in.close();
        out.close();
    }
}
