package com.yxsd.kanshu.product.service;

import com.yxsd.kanshu.base.service.IBaseService;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.product.model.Book;

import java.util.List;
import java.util.Map;

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

    /**
     * 分页获取图书信息
     * @param query
     * @return
     */
    PageFinder<Book> findPageFinderWithExpandObjs(Object params, Query query);

    /**
     * 根据条件查询单个图书
     * @param condition
     * @return
     */
    Book selectOneBookCondition(Map<String,Object> condition);

    /**
     * 查询新书
     * @return
     */
    List<Book> selectNewBook();

    /**
     * 清除图书相关缓存
     * @param bookId
     */
    public void clearBookAllCache(Long bookId);
}
