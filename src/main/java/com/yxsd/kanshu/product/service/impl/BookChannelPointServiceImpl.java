package com.yxsd.kanshu.product.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.product.dao.IBookChannelPointDao;
import com.yxsd.kanshu.product.model.BookChannelPoint;
import com.yxsd.kanshu.product.service.IBookChannelPointService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2018/1/13.
 */
@Service(value="bookChannelPointService")
public class BookChannelPointServiceImpl extends BaseServiceImpl<BookChannelPoint, Long> implements IBookChannelPointService {

    @Resource(name="bookChannelPointDao")
    private IBookChannelPointDao bookChannelPointDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,BookChannelPoint> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,BookChannelPoint> slaveRedisTemplate;

    @Override
    public IBaseDao<BookChannelPoint> getBaseDao() {
        return bookChannelPointDao;
    }

    @Override
    public BookChannelPoint getBookChannelPoint() {
        String key = RedisKeyConstants.CACHE_BOOK_CHANNEL_POINT_KEY;
        BookChannelPoint bookChannelPoint = slaveRedisTemplate.opsForValue().get(key);
        if(bookChannelPoint == null){
            List<BookChannelPoint> bookChannelPoints =  this.findListByParamsObjs(null);
            if(CollectionUtils.isNotEmpty(bookChannelPoints)){
                bookChannelPoint = bookChannelPoints.get(0);
                masterRedisTemplate.opsForValue().set(key, bookChannelPoint, 5, TimeUnit.DAYS);
            }
        }
        return bookChannelPoint;
    }
}
