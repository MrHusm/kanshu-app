package com.yxsd.kanshu.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.yxsd.kanshu.base.contants.Constants;
import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.ucenter.dao.IUserDao;
import com.yxsd.kanshu.ucenter.model.*;
import com.yxsd.kanshu.ucenter.service.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements IUserService {

    @Resource(name="userDao")
    private IUserDao userDao;

    @Resource(name="userAccountService")
    private IUserAccountService userAccountService;

    @Resource(name="userVipService")
    private IUserVipService userVipService;

    @Resource(name="userAccountLogService")
    private IUserAccountLogService userAccountLogService;

    @Resource(name="userPayChapterService")
    IUserPayChapterService userPayChapterService;

    @Resource(name="userPayBookService")
    IUserPayBookService userPayBookService;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,User> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,User> slaveRedisTemplate;

    @Override
    public IBaseDao<User> getBaseDao() {
        return userDao;
    }


    @Override
    public User getUserByUserId(Long userId) {
        String key = RedisKeyConstants.CACHE_USER_ID_KEY + userId;
        User user = slaveRedisTemplate.opsForValue().get(key);
        if(user == null){
            user = this.get(userId);
            if(user != null){
                UserVip userVip = this.userVipService.findUniqueByParams("userId",userId);
                if(userVip != null){
                    Date now = new Date();
                    if(userVip.getEndDate().getTime() > now.getTime()){
                        user.setVip(true);
                    }
                }
                masterRedisTemplate.opsForValue().set(key, user, 5, TimeUnit.HOURS);
            }
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int charge(Long userId, Integer price, Integer type, Map<String,Object> map) {
        UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);
        UserAccountLog userAccountLog = new UserAccountLog();
        if(type == Constants.CONSUME_TYPE_S1){
            //单章购买
            Long bookId = Long.parseLong(map.get("bookId").toString());
            Long chapterId = Long.parseLong(map.get("chapterId").toString());
            Integer channel = map.get("channel") == null ? null : Integer.parseInt(map.get("channel").toString());
            if((userAccount.getMoney() + userAccount.getVirtualMoney()) >= price){
                UserPayChapter userPayChapter = new UserPayChapter();
                userPayChapter.setOrderNo(Long.toHexString(System.currentTimeMillis()));
                userPayChapter.setBookId(bookId);
                userPayChapter.setChapterId(chapterId);
                userPayChapter.setUserId(userId);
                userPayChapter.setUpdateDate(new Date());
                userPayChapter.setCreateDate(new Date());
                //保存章节购买数据
                userPayChapterService.save(userPayChapter);

                userAccountLog.setChannel(channel);
                userAccountLog.setOrderNo(userPayChapter.getOrderNo());
                userAccountLog.setProductId(String.valueOf(bookId));
                userAccountLog.setComment(JSON.toJSONString(map));
            }else{
                //余额不够
                return -1;
            }
        }else if(type == Constants.CONSUME_TYPE_S2) {
            //批量购买
            Long bookId = Long.parseLong(map.get("bookId").toString());
            Long startChapterId = Long.parseLong(map.get("startChapterId").toString());
            Integer startChapterIdx = Integer.parseInt(map.get("startChapterIdx").toString());
            Long endChapterId = Long.parseLong(map.get("endChapterId").toString());
            Integer endChapterIdx = Integer.parseInt(map.get("endChapterIdx").toString());
            Integer channel = map.get("channel") == null ? null : Integer.parseInt(map.get("channel").toString());
            if ((userAccount.getMoney() + userAccount.getVirtualMoney()) >= price) {
                UserPayBook userPayBook = new UserPayBook();
                userPayBook.setBookId(bookId);
                userPayBook.setOrderNo(Long.toHexString(System.currentTimeMillis()));
                userPayBook.setStartChapterId(startChapterId);
                userPayBook.setStartChapterIdx(startChapterIdx);
                userPayBook.setEndChapterId(endChapterId);
                userPayBook.setEndChapterIdx(endChapterIdx);
                userPayBook.setType(1);
                userPayBook.setUserId(userId);
                userPayBook.setCreateDate(new Date());
                userPayBook.setUpdateDate(new Date());

                //保存批量购买数据
                userPayBookService.save(userPayBook);

                userAccountLog.setChannel(channel);
                userAccountLog.setOrderNo(userPayBook.getOrderNo());
                userAccountLog.setProductId(String.valueOf(bookId));
                userAccountLog.setComment(JSON.toJSONString(map));
            } else {
                //余额不够
                return -1;
            }
        }else if(type == Constants.CONSUME_TYPE_S3){
            //批量购买
            Long bookId = Long.parseLong(map.get("bookId").toString());
            Integer channel = map.get("channel") == null ? null : Integer.parseInt(map.get("channel").toString());
            if((userAccount.getMoney() + userAccount.getVirtualMoney()) >= price) {
                UserPayBook userPayBook = new UserPayBook();
                userPayBook.setBookId(bookId);
                userPayBook.setOrderNo(Long.toHexString(System.currentTimeMillis()));
                userPayBook.setType(2);
                userPayBook.setUserId(userId);
                userPayBook.setCreateDate(new Date());
                userPayBook.setUpdateDate(new Date());

                //保存批量购买数据
                userPayBookService.save(userPayBook);

                userAccountLog.setChannel(channel);
                userAccountLog.setOrderNo(userPayBook.getOrderNo());
                userAccountLog.setProductId(String.valueOf(bookId));
                userAccountLog.setComment(JSON.toJSONString(map));
            } else {
                //余额不够
                return -1;
            }
        }
        if(type < 0){
            //消费
            if(userAccount.getVirtualMoney() >= price){
                userAccount.setVirtualMoney(userAccount.getVirtualMoney() - price);

                userAccountLog.setUnitMoney(0);
                userAccountLog.setUnitVirtual(price);
            }else{
                userAccountLog.setUnitMoney(price - userAccount.getVirtualMoney());
                userAccountLog.setUnitVirtual(userAccount.getVirtualMoney());

                userAccount.setMoney(userAccount.getMoney() - (price - userAccount.getVirtualMoney()));
                userAccount.setVirtualMoney(0);
            }
        }else{
            //充钱
            //TODO
        }

        userAccountLog.setUserId(userId);
        userAccountLog.setType(type);
        userAccountLog.setCreateDate(new Date());
        userAccount.setUpdateDate(new Date());
        //修改账户数据
        userAccountService.update(userAccount);
        //保存账户日志数据
        userAccountLogService.save(userAccountLog);

        return 0;
    }
}
