package com.kanshu.kanshu.product.service.impl;

import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.product.service.IBookService;
import com.kanshu.chapter.base.service.IChapterService;
import com.kanshu.kanshu.product.dao.IBookDao;
import com.kanshu.kanshu.product.model.Book;
import com.kanshu.kanshu.product.model.Chapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lenovo on 2017/8/7.
 */
@Service(value="bookService")
public class BookServiceImpl extends BaseServiceImpl<Book, Long> implements IBookService {

    @Resource(name="bookDao")
    private IBookDao bookDao;

    @Resource(name="chapterService")
    private IChapterService chapterService;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,Chapter> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,Chapter> slaveRedisTemplate;

    @Override
    public IBaseDao<Book> getBaseDao() {
        return bookDao;
    }


}
