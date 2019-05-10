package com.teradata.intenet.proxy.jdk;

/**
 * @Project:
 * @Description:
 * @Version 1.0.0
 * @Throws SystemException:
 * @Author: <li>2019/1/30/030 Administrator Create 1.0
 * @Copyright ©2018-2019 al.github
 * @Modified By:
 */
public class BookFacade implements IBookFacade{
    @Override
    public void addBook(String name) {
        System.out.println("新增加图书!"+name);
    }

    @Override
    public void deleteBook() {
        System.out.println("直接删除图书!");
    }
}
