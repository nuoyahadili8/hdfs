package com.teradata.intenet.mysql;

import java.sql.Connection;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/1/001 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class JDBCMysql {

    public static void main(String[] args) {
        JdbcUtils jdbcUtils=new JdbcUtils();
        Connection connection = jdbcUtils.getConnection();


    }
}
