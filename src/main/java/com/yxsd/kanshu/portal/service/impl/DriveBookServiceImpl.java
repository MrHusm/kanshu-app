package com.yxsd.kanshu.portal.service.impl;

import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.base.utils.PageFinder;
import com.yxsd.kanshu.base.utils.Query;
import com.yxsd.kanshu.portal.dao.IDriveBookDao;
import com.yxsd.kanshu.portal.model.DriveBook;
import com.yxsd.kanshu.portal.service.IDriveBookService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="driveBookService")
public class DriveBookServiceImpl extends BaseServiceImpl<DriveBook, Long> implements IDriveBookService {

    @Resource(name="driveBookDao")
    private IDriveBookDao driveBookDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,DriveBook> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,DriveBook> slaveRedisTemplate;

    @Override
    public IBaseDao<DriveBook> getBaseDao() {
        return driveBookDao;
    }

    @Override
    public List<DriveBook> getDriveBooks(Integer type) {
        String key = RedisKeyConstants.CACHE_DRIVE_BOOK_KEY + type;
        List<DriveBook> driveBooks = null;
        if(masterRedisTemplate.hasKey(key)){
            driveBooks = slaveRedisTemplate.opsForList().range(key,0,-1);
        }
        if(CollectionUtils.isEmpty(driveBooks)){
            driveBooks = this.findListByParams("type",type);
            if(CollectionUtils.isNotEmpty(driveBooks)){
                for(int i = 0; i < driveBooks.size(); i++){
                    masterRedisTemplate.opsForList().rightPush(key,driveBooks.get(i));
                }
                masterRedisTemplate.expire(key,1, TimeUnit.DAYS);
            }else{
                return null;
            }
        }
        return driveBooks;

    }

    @Override
    public DriveBook getDriveBookByCondition(Integer type, Long bookId) {
        String key = String.format(RedisKeyConstants.CACHE_DRIVE_BOOK_ONE_KEY,type,bookId);
        DriveBook driveBook = slaveRedisTemplate.opsForValue().get(key);
        if(driveBook == null){
            driveBook = this.findUniqueByParams("type",type,"bookId",bookId);
            if(driveBook != null){
                masterRedisTemplate.opsForValue().set(key,driveBook,1,TimeUnit.DAYS);
            }
        }
        return driveBook;
    }

    @Override
    public PageFinder<DriveBook> findPageWithCondition(Integer type, Query query) {
        List<DriveBook> driveBooks = this.getDriveBooks(type);
        PageFinder<DriveBook> pageFinder = new PageFinder<DriveBook>(query.getPage(),query.getPageSize(), 0);
        if(CollectionUtils.isNotEmpty(driveBooks)){
            int end = (query.getOffset() + query.getPageSize()) > driveBooks.size() ? driveBooks.size() : (query.getOffset() + query.getPageSize());
            List<DriveBook> datas = driveBooks.subList(query.getOffset(), end);
            pageFinder = new PageFinder<DriveBook>(query.getPage(),query.getPageSize(), driveBooks.size(), datas);
        }
        return pageFinder;
    }
}
