package com.kanshu.kanshu.portal.service.impl;

import com.kanshu.kanshu.base.contants.RedisKeyConstants;
import com.kanshu.kanshu.base.dao.IBaseDao;
import com.kanshu.kanshu.base.service.impl.BaseServiceImpl;
import com.kanshu.kanshu.portal.service.IDriveBookService;
import com.kanshu.kanshu.portal.dao.IDriveBookDao;
import com.kanshu.kanshu.portal.model.DriveBook;
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
        List<DriveBook> driveBooks = slaveRedisTemplate.opsForList().range(key,0,-1);
        if(CollectionUtils.isEmpty(driveBooks)){
            driveBooks = this.findListByParams("type",type);
            if(CollectionUtils.isNotEmpty(driveBooks)){
                for(int i = 0; i < driveBooks.size(); i++){
                    masterRedisTemplate.opsForList().set(key,i,driveBooks.get(i));
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
            }else{
                return null;
            }
        }
        return driveBook;
    }
}
