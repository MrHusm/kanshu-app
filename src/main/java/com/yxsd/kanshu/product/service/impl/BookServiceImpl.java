package com.yxsd.kanshu.product.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.product.dao.IBookDao;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.service.IBookService;
import com.yxsd.kanshu.product.service.IChapterService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
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

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,List<Map<String,Object>>> listMapMasterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,List<Map<String,Object>>> listMapSlaveRedisTemplate;

    @Override
    public IBaseDao<Book> getBaseDao() {
        return bookDao;
    }


    @Override
    public Book getBookById(Long bookId) {
        String key = RedisKeyConstants.CACHE_BOOK_KEY+bookId;
        Book book = this.slaveRedisTemplate.opsForValue().get(key);
        if(book == null){
            book = this.findUniqueByParams("bookId",bookId);
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

    @Override
    public Book selectOneBookCondition(Map<String,Object> condition){
        return getBaseDao().selectOne(getPrefix()+"selectOneBookCondition",condition);
    }

    @Override
    public List<Map<String,Object>> getBooksByAuthorId(Long authorId) {
        String key = RedisKeyConstants.CACHE_BOOKS_AUTHOR_KEY+authorId;
        List<Map<String,Object>> result = this.listMapSlaveRedisTemplate.opsForValue().get(key);
        if(CollectionUtils.isEmpty(result)){
            List<Book> books = findListByParams("authorId",authorId);
            if(CollectionUtils.isNotEmpty(books)){
                result = bookToMap(books);
                this.listMapSlaveRedisTemplate.opsForValue().set(key,result,1, TimeUnit.DAYS);
            }
        }
        return result;
    }

    @Override
    public List<Map<String,Object>> getHighClickBooksByCid(Long categorySecId) {
        String key = RedisKeyConstants.CACHE_BOOKS_HIGH_CLICK_CID_KEY+categorySecId;
        List<Map<String,Object>> list = this.listMapSlaveRedisTemplate.opsForValue().get(key);
        if(CollectionUtils.isEmpty(list)){
            Query query = new Query();
            query.setPage(1);
            query.setPageSize(100);
            Book condition = new Book();
            condition.setCategorySecId(categorySecId);
            PageFinder<Book> pageFinder = findPageFinderWithExpandObjs(condition, query);
            if(pageFinder != null && CollectionUtils.isNotEmpty(pageFinder.getData())){
                Collections.shuffle(pageFinder.getData());
                List<Book> books = null;
                if(pageFinder.getData().size() > 13){
                    books = pageFinder.getData().subList(0,13);
                }else{
                    books = pageFinder.getData();
                }
                list = bookToMap(books);
                this.listMapMasterRedisTemplate.opsForValue().set(key,list,1, TimeUnit.DAYS);
            }
        }
        return list;
    }

    private List<Map<String,Object>> bookToMap(List<Book> books){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(Book book : books) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("bookId", book.getBookId());
            map.put("title", book.getTitle());
            map.put("authorPenname", book.getAuthorPenname());
            map.put("coverUrl", book.getCoverUrl());
            map.put("categorySecName", book.getCategorySecName());
            map.put("categoryThrName", book.getCategoryThrName());
            if(book.getIntro().length() > 200){
                map.put("intro", book.getIntro().substring(0,200));
            }else{
                map.put("intro", book.getIntro());
            }

            list.add(map);
        }
        return list;
    }

    @Override
    public List<Book> selectNewBook() {
        return getBaseDao().selectList(getPrefix()+"selectNewBook");
    }

    @Override
    public void clearBookAllCache(Long bookId) {
        logger.info("开始清除图书"+bookId+"相关缓存");
        try{
            //图书目录缓存key
            String bookCatalogKey = RedisKeyConstants.CACHE_BOOK_CATALOG_KEY + String.valueOf(bookId);
            masterRedisTemplate.delete(bookCatalogKey);

            //图书驱动具体图书key
            Set<String> driveBookOneKeys = masterRedisTemplate.keys("drive_book_type*bid_"+String.valueOf(bookId));
            if(CollectionUtils.isNotEmpty(driveBookOneKeys)){
                for(String key : driveBookOneKeys){
                    masterRedisTemplate.delete(key);
                }
            }

            //图书信息key
            String bookKey = RedisKeyConstants.CACHE_BOOK_KEY + String.valueOf(bookId);
            masterRedisTemplate.delete(bookKey);
        }catch (Exception e){
            logger.info("清除图书"+bookId+"相关缓存异常");
            e.printStackTrace();
        }
        logger.info("结束清除图书"+bookId+"相关缓存");
    }


}
