package com.teradata.intenet.thread;

import java.util.Iterator;
import java.util.Map;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/9/009 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class SystemEnv {

    public static void main(String[] args) {
        Map<String, String> map = System.getenv();

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.print(entry.getKey() + "=");
            System.out.println(entry.getValue());
        }

        System.out.println(map.get("SFTP_SAVE_PATH"));
    }
}
