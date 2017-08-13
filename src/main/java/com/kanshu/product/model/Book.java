package com.kanshu.product.model;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/8/12.
 */
public class Book implements Serializable {

    private Long bookId;

    /**
     * 书名
     */
    private String title;

    /**
     * 图书封面
     */
    private String img;

    /**
     * 作者名
     */
    private String author;

    /**
     * 笔名
     */
    private String authorPenname;

}
