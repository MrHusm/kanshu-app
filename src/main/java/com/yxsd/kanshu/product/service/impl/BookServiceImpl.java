package com.yxsd.kanshu.product.service.impl;

import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.product.service.IChapterService;
import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.product.dao.IBookDao;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.service.IBookService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

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
    private RedisTemplate<String,Book> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,Book> slaveRedisTemplate;

    @Override
    public IBaseDao<Book> getBaseDao() {
        return bookDao;
    }


    @Override
    public Book getBookById(Long bookId) {
        String key = RedisKeyConstants.CACHE_BOOK_KEY+bookId;
        Book book = this.slaveRedisTemplate.opsForValue().get(key);
        if(book == null){
            book = this.findUniqueByParams("bookId",bookId,"shelfStatus",1);
            if(book != null){
                this.masterRedisTemplate.opsForValue().set(key,book,5, TimeUnit.HOURS);
            }
        }
        return book;
    }

    @Override
    public PageFinder<Book> findPageFinderWithExpandObjs(Object params, Query query) {
        params = convertBeanToMap(params);
        return getBaseDao().getPageFinderObjs(params, query, getPrefix()+"pageCount", getPrefix()+"pageWithExpandData");
    }
}
