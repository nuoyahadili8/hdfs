package com.teradata.hadoop.sql;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/6/12/012 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class SqlFilter {

    public static List<String> getFileList(final String scriptPath) throws Exception {
        final List<String> sqlList = new ArrayList<String>();
        final Configuration conf = new Configuration();
        final FileSystem fs = FileSystem.get(new URI(scriptPath), conf);
        final Path sqlfile = new Path(scriptPath);
        FSDataInputStream sqlFileIn = null;
        try {
            sqlFileIn = fs.open(sqlfile);
            final BufferedReader bf = new BufferedReader(new InputStreamReader((InputStream)sqlFileIn));
            String str;
            while ((str = bf.readLine()) != null) {
                sqlList.add(str);
            }
            bf.close();
            sqlFileIn.close();
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return sqlList;
    }

    public static List<String> dealSqlList(final List<String> liststr) {
        final List<String> newArrayList = new ArrayList<String>();
        StringBuffer stringBuffer = new StringBuffer("");
        Boolean flag = false;
        for (final String str : liststr) {
            if (str.trim().startsWith("/**")) {
                flag = true;
            }
            if (!flag) {
                if (str.trim().startsWith("--")) {
                    newArrayList.add(str);
                    stringBuffer = new StringBuffer("");
                }
                else if (str.trim().endsWith(";")) {
                    stringBuffer.append(str);
                    newArrayList.add(stringBuffer.toString());
                    stringBuffer = new StringBuffer("");
                }
                else {
                    stringBuffer.append(str + "\r\n");
                }
            }
            else {
                stringBuffer.append(str + "\r\n");
                if (!str.trim().endsWith("**/")) {
                    continue;
                }
                newArrayList.add(stringBuffer.toString());
                stringBuffer = new StringBuffer("");
                flag = false;
            }
        }
        return newArrayList;
    }

    public static String setSqlParams(String sql, final Map<String, String> map) {
        final Set<Map.Entry<String, String>> sets = map.entrySet();
        for (final Map.Entry<String, String> entry : sets) {
            final String regex = "\\$\\{" + entry.getKey() + "\\}";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(sql);
            sql = matcher.replaceAll(entry.getValue());
        }
        return sql;
    }

    public static void main(String[] args) throws IOException {
        List<String> old_sql = IOUtils.readLines(new FileInputStream("F:\\Domcument\\win7_180227\\data\\CDH迁移Gbase平台\\脚本\\TB_RPT_HIGH_DOU_WLAN_MON.SQL"),"utf-8");
        List<String> sqls = dealSqlList(old_sql);
        for (String sql:sqls){
            System.out.println("================");
            System.out.println(sql);
        }

    }
}
