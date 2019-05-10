package com.github.al.limit.spi;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/3/19/019 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class ImageHello implements HelloInterface{
    @Override
    public void sayHello() {
        System.out.println("Image Hello");
    }
}
