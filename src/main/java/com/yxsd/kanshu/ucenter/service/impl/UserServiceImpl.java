package com.yxsd.kanshu.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.yxsd.kanshu.base.contants.Constants;
import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.dao.IBaseDao;
import com.yxsd.kanshu.base.service.impl.BaseServiceImpl;
import com.yxsd.kanshu.base.utils.HttpUtils;
import com.yxsd.kanshu.pay.model.RechargeItem;
import com.yxsd.kanshu.pay.service.IRechargeItemService;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.model.BookExpand;
import com.yxsd.kanshu.product.service.IBookExpandService;
import com.yxsd.kanshu.product.service.IBookService;
import com.yxsd.kanshu.ucenter.dao.IUserDao;
import com.yxsd.kanshu.ucenter.model.*;
import com.yxsd.kanshu.ucenter.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Resource(name="userReceiveService")
    private IUserReceiveService userReceiveService;

    @Resource(name="userAccountLogService")
    private IUserAccountLogService userAccountLogService;

    @Resource(name="userPayChapterService")
    IUserPayChapterService userPayChapterService;

    @Resource(name="userPayBookService")
    IUserPayBookService userPayBookService;

    @Resource(name="rechargeItemService")
    IRechargeItemService rechargeItemService;

    @Resource(name="bookExpandService")
    IBookExpandService bookExpandService;

    @Resource(name="bookService")
    IBookService bookService;

    @Resource(name="userUuidService")
    private IUserUuidService userUuidService;

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
                UserReceive userReceive = this.userReceiveService.findUniqueByParams("userId",userId);
                if(userReceive != null){
                    //是否是游客账号
                    if((userReceive.getTelStatus() != null && userReceive.getTelStatus() == 1)
                            || (userReceive.getQqStatus() != null && userReceive.getQqStatus() == 1)
                            || (userReceive.getWeiboStatus() != null && userReceive.getWeiboStatus() == 1)
                            || (userReceive.getWeixinStatus() != null && userReceive.getWeixinStatus() == 1)){
                        user.setTourist(false);
                    }
                }
                masterRedisTemplate.opsForValue().set(key, user, 5, TimeUnit.HOURS);
            }
        }
        return user;
    }

    @Override
    public User register(String channel,String deviceType,String deviceSerialNo,HttpServletRequest request) {
        UserUuid userUuid = new UserUuid();
        userUuid.setCreateDate(new Date());
        userUuidService.save(userUuid);
        User user = new User();
        user.setName("v"+userUuid.getId());
        user.setNickName("v"+userUuid.getId());
        user.setPassword("v"+Long.toHexString(System.currentTimeMillis()));
        user.setDeviceType(deviceType);
        user.setDeviceSerialNo(deviceSerialNo);
        user.setLogo(HttpUtils.getBasePath(request) + "/img/user_logo_default.jpg");
        if(StringUtils.isNotBlank(channel)){
            user.setChannel(Integer.parseInt(channel));
            user.setChannelNow(Integer.parseInt(channel));
        }
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        save(user);
        //保存账号相关信息
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(user.getUserId());
        userAccount.setMoney(0);
        userAccount.setVirtualMoney(0);
        userAccount.setCreateDate(new Date());
        userAccount.setUpdateDate(new Date());
        userAccountService.save(userAccount);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int consume(Long userId, Integer price, Integer type, Map<String,Object> map) {
            UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);
            UserAccountLog userAccountLog = new UserAccountLog();
            Long bookId = Long.parseLong(map.get("bookId").toString());
            if(type == Constants.CONSUME_TYPE_S1){
                //单章购买
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
                //全本购买
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

            userAccountLog.setUserId(userId);
            userAccountLog.setType(type);
            userAccountLog.setCreateDate(new Date());
            userAccount.setUpdateDate(new Date());
            //修改账户数据
            userAccountService.update(userAccount);
            //保存账户日志数据
            userAccountLogService.save(userAccountLog);

            //统计销量
            BookExpand bookExpand = this.bookExpandService.findUniqueByParams("bookId",bookId);
            if(bookExpand == null){
                Book book = this.bookService.getBookById(bookId);
                bookExpand = new BookExpand();
                bookExpand.setBookId(bookId);
                bookExpand.setBookName(book.getTitle());
                bookExpand.setSaleNum(1L);
                bookExpand.setCreateDate(new Date());
                bookExpand.setUpdateDate(new Date());
                bookExpandService.save(bookExpand);
            }else{
                bookExpand.setSaleNum((bookExpand.getSaleNum() ==  null ? 0 : bookExpand.getSaleNum()) + 1);
                bookExpand.setUpdateDate(new Date());
                bookExpandService.update(bookExpand);
            }
            return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void charge(Long userId, Integer type, Integer channel, String orderNo, Long rechargeItemId) {
        UserAccountLog accountLog = new UserAccountLog();
        accountLog.setUserId(userId);
        accountLog.setChannel(channel);
        accountLog.setOrderNo(orderNo);
        accountLog.setType(type);
        accountLog.setCreateDate(new Date());

        //充值
        RechargeItem rechargeItem = this.rechargeItemService.get(rechargeItemId);
        //用户账户充值
        UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);
        userAccount.setMoney(userAccount.getMoney() + rechargeItem.getMoney());
        if(rechargeItem.getVirtual() != null){
            userAccount.setVirtualMoney(userAccount.getVirtualMoney() + rechargeItem.getVirtual());
        }
        userAccount.setUpdateDate(new Date());
        this.userAccountService.update(userAccount);

        accountLog.setUnitMoney(rechargeItem.getMoney());
        accountLog.setUnitVirtual(rechargeItem.getVirtual());
        accountLog.setComment(String.valueOf(rechargeItem.getPrice().intValue()));

        //保存消费日志表
        userAccountLogService.save(accountLog);

    }


}
