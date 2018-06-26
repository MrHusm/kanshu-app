package com.yxsd.kanshu.portal.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.portal.dao.IDriveBookDao;
import com.yxsd.kanshu.portal.model.DriveBook;
import com.yxsd.kanshu.portal.service.IDriveBookService;
import com.yxsd.kanshu.product.service.IBookChannelPointService;
import com.yxsd.kanshu.product.service.IBookPointService;
import com.yxsd.kanshu.product.service.IBookService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="driveBookService")
public class DriveBookServiceImpl extends BaseServiceImpl<DriveBook, Long> implements IDriveBookService {

    @Resource(name="driveBookDao")
    private IDriveBookDao driveBookDao;

    @Resource(name="bookChannelPointService")
    private IBookChannelPointService bookChannelPointService;

    @Resource(name="bookPointService")
    private IBookPointService bookPointService;

    @Resource(name="bookService")
    IBookService bookService;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,DriveBook> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,DriveBook> slaveRedisTemplate;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,List<DriveBook>> listMasterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,List<DriveBook>> listSlaveRedisTemplate;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,List<Map<String, Object>>> freeBooksMasterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,List<Map<String, Object>>> freeBooksSlaveRedisTemplate;

    @Override
    public IBaseDao<DriveBook> getBaseDao() {
        return driveBookDao;
    }

    @Override
    public List<DriveBook> getDriveBooks(Integer type,Integer status) {
        String key =String.format(RedisKeyConstants.CACHE_DRIVE_BOOK_KEY, type, status);
        List<DriveBook> driveBooks = null;
        if(listMasterRedisTemplate.hasKey(key)){
            driveBooks = listSlaveRedisTemplate.opsForValue().get(key);
        }
        if(CollectionUtils.isEmpty(driveBooks)){
            driveBooks = this.findListByParams("type",type,"status",status);
            if(CollectionUtils.isNotEmpty(driveBooks)){
                listMasterRedisTemplate.opsForValue().set(key,driveBooks,1,TimeUnit.DAYS);
            }
        }
        return driveBooks;
    }

    @Override
    public DriveBook getDriveBookByCondition(Integer type, Long bookId,Integer status) {
        String key = String.format(RedisKeyConstants.CACHE_DRIVE_BOOK_ONE_KEY,type,bookId,status);
        DriveBook driveBook = slaveRedisTemplate.opsForValue().get(key);
        if(driveBook == null){
            List<DriveBook> driveBooks = this.findListByParams("type",type,"bookId",bookId,"status",status);
            if(CollectionUtils.isNotEmpty(driveBooks)) {
                driveBook = driveBooks.get(0);
                masterRedisTemplate.opsForValue().set(key,driveBook,1,TimeUnit.DAYS);
            }
        }
        return driveBook;
    }

    @Override
    public PageFinder<DriveBook> findPageWithCondition(Integer type, Query query) {
        List<DriveBook> driveBooks = this.getDriveBooks(type,1);
        PageFinder<DriveBook> pageFinder = new PageFinder<DriveBook>(query.getPage(),query.getPageSize(), 0);
        if(CollectionUtils.isNotEmpty(driveBooks)){
            int end = (query.getOffset() + query.getPageSize()) > driveBooks.size() ? driveBooks.size() : (query.getOffset() + query.getPageSize());
            List<DriveBook> datas = driveBooks.subList(query.getOffset(), end);
            pageFinder = new PageFinder<DriveBook>(query.getPage(),query.getPageSize(), driveBooks.size(), datas);
        }
        return pageFinder;
    }

    @Override
    public List<Map<String, Object>> getTimeFreeBooks(String channel) {
        String key = RedisKeyConstants.CACHE_TIME_FREE_BOOK_KEY;
        List<Map<String, Object>> data = null;
        if(freeBooksMasterRedisTemplate.hasKey(key)){
            data = freeBooksSlaveRedisTemplate.opsForValue().get(key);
        }
        if(CollectionUtils.isEmpty(data)){
            data = new ArrayList<Map<String, Object>>();
            //限时免费图书
            List<DriveBook> freeBooks = findListByParams("type",9,"status",1,"manType",1);
            if(CollectionUtils.isNotEmpty(freeBooks)){
                Map<String,Object> freeMap = new HashMap<String, Object>();
                freeMap.put("type",1);
                freeMap.put("data",freeBooks);
                data.add(freeMap);
            }
            //限章免费图书
            List<DriveBook> freeChapterBooks = findListByParams("type",11,"status",1,"manType",1);
            if(CollectionUtils.isNotEmpty(freeChapterBooks)){
                Map<String,Object> freeChapterMap = new HashMap<String, Object>();
                freeChapterMap.put("type",2);
                freeChapterMap.put("data",freeChapterBooks);
                data.add(freeChapterMap);
            }
//            BookChannelPoint bookChannelPoint = this.bookChannelPointService.getBookChannelPoint();
//            if(bookChannelPoint != null){
//                String channels = bookChannelPoint.getChannels();
//                if (channels.contains(channel)) {
//                    Map<Long, Integer> bookPointMap = bookPointService.getBookPointMap();
//                    Map<String,Object> condition = new HashMap<String,Object>();
//                    condition.put("bookIds",bookPointMap.keySet());
//                    List<Book> freeChapterBooks = this.bookService.findListByParamsObjs(condition);
//                    if(CollectionUtils.isNotEmpty(freeChapterBooks)){
//                        Map<String,Object> freeChapterMap = new HashMap<String, Object>();
//                        freeChapterMap.put("type",2);
//                        freeChapterMap.put("data",freeChapterBooks);
//                        data.add(freeChapterMap);
//                    }
//                }
//            }

            if(CollectionUtils.isNotEmpty(data)){
                freeBooksMasterRedisTemplate.opsForValue().set(key,data,6,TimeUnit.HOURS);
            }
        }
        return data;
    }
}
