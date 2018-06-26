package com.yxsd.kanshu.product.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.portal.model.DriveBook;
import com.yxsd.kanshu.portal.service.IDriveBookService;
import com.yxsd.kanshu.product.dao.IBookPointDao;
import com.yxsd.kanshu.product.model.BookChannelPoint;
import com.yxsd.kanshu.product.model.BookPoint;
import com.yxsd.kanshu.product.service.IBookChannelPointService;
import com.yxsd.kanshu.product.service.IBookPointService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2018/1/13.
 */
@Service(value="bookPointService")
public class BookPointServiceImpl extends BaseServiceImpl<BookPoint, Long> implements IBookPointService {

    @Resource(name="bookPointDao")
    private IBookPointDao bookPointDao;

    @Resource(name="bookChannelPointService")
    private IBookChannelPointService bookChannelPointService;

    @Resource(name="driveBookService")
    private IDriveBookService driveBookService;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,Map<Long,Integer>> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,Map<Long,Integer>> slaveRedisTemplate;

    @Override
    public IBaseDao<BookPoint> getBaseDao() {
        return bookPointDao;
    }

    @Override
    public Integer getBookPointNum(Long bookId, String channel) {
        //默认从31章开始收费
        Integer num = 31;
        //获取限章免费的图书
        DriveBook driveBook = this.driveBookService.findUniqueByParams("type",11,"bookId",bookId,"status",1,"manType",1);
        if(driveBook != null){
            num = driveBook.getNum();
        }else{
            if(StringUtils.isNotBlank(channel)){
                BookChannelPoint bookChannelPoint = this.bookChannelPointService.getBookChannelPoint();
                if(bookChannelPoint != null){
                    String channels = bookChannelPoint.getChannels();
                    if(channels.contains(channel)){
                        Map<Long,Integer> bookPointMap = getBookPointMap();
                        if(bookPointMap != null && bookPointMap.get(bookId) !=null){
                            num = bookPointMap.get(bookId);
                        }
                    }
                }
            }
        }
        return num;
    }

    @Override
    public Map<Long,Integer> getBookPointMap() {
        String key = RedisKeyConstants.CACHE_BOOK_POINT_MAP_KEY;
        Map<Long,Integer> bookPointMap = slaveRedisTemplate.opsForValue().get(key);
        if(bookPointMap == null){
            List<BookPoint> bookPoints =  this.findListByParamsObjs(null);
            if(CollectionUtils.isNotEmpty(bookPoints)){
                bookPointMap = new HashMap<Long, Integer>();
                for(BookPoint bookPoint : bookPoints){
                    bookPointMap.put(bookPoint.getBookId(),bookPoint.getNum());
                }
                masterRedisTemplate.opsForValue().set(key, bookPointMap, 1, TimeUnit.DAYS);
            }
        }
        return bookPointMap;
    }
}
