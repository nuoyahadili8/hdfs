package com.github.al.limit.spi;

import java.util.ServiceLoader;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/19/019 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class SPIMain {

    public static void main(String[] args) {
        ServiceLoader<HelloInterface> loaders = ServiceLoader.load(HelloInterface.class);

        for (HelloInterface in : loaders) {
            in.sayHello();
        }
    }
}
