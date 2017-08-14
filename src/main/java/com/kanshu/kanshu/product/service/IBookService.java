package com.kanshu.kanshu.product.service;

import com.kanshu.kanshu.base.service.IBaseService;
import com.kanshu.kanshu.product.model.Book;

/**
 * Created by lenovo on 2017/8/7.
 */
public interface IBookService extends IBaseService<Book,Long> {

    /**
     * 根据图书id获取图书信息
     * @param bookId
     * @return
     */
    public Book getBookById(Long bookId);
}
