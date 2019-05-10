package com.teradata.hadoop.hive;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2018/12/28/028 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class MD5 extends UDF {
    public String evaluate(final String s) {
        if(Strings.isNullOrEmpty(s.trim())){
            return null;
        }
        return Hashing.md5().hashString(s.trim()).toString();
    }
}
