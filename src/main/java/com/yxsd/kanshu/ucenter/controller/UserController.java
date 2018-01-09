package com.yxsd.kanshu.ucenter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxsd.kanshu.base.contants.Constants;
import com.yxsd.kanshu.base.contants.ErrorCodeEnum;
import com.yxsd.kanshu.base.contants.RedisKeyConstants;
import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.*;
import com.yxsd.kanshu.pay.service.IRechargeItemService;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.model.BookExpand;
import com.yxsd.kanshu.product.model.Chapter;
import com.yxsd.kanshu.product.service.IBookExpandService;
import com.yxsd.kanshu.product.service.IBookService;
import com.yxsd.kanshu.product.service.IChapterService;
import com.yxsd.kanshu.ucenter.model.*;
import com.yxsd.kanshu.ucenter.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author hushengmeng
 * @date 2017/7/4.
 */
@Controller
@Scope("prototype")
@RequestMapping("user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name="userService")
    IUserService userService;

    @Resource(name="userAccountService")
    IUserAccountService userAccountService;

    @Resource(name="userAccountLogService")
    IUserAccountLogService userAccountLogService;

    @Resource(name="userUuidService")
    IUserUuidService userUuidService;

    @Resource(name="userQqService")
    IUserQqService userQqService;

    @Resource(name="userWeixinService")
    IUserWeixinService userWeixinService;

    @Resource(name="userWeiboService")
    IUserWeiboService userWeiboService;

    @Resource(name="versionInfoService")
    IVersionInfoService versionInfoService;

    @Resource(name="rechargeItemService")
    IRechargeItemService rechargeItemService;

    @Resource(name="bookService")
    IBookService bookService;

    @Resource(name="chapterService")
    IChapterService chapterService;

    @Resource(name="bookExpandService")
    IBookExpandService bookExpandService;

    @Resource(name="userReceiveService")
    IUserReceiveService userReceiveService;

    @Resource(name="userVipService")
    IUserVipService userVipService;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate masterRedisTemplate;

    /**
     * 手机号登录
     * @param response
     * @param request
     */
    @RequestMapping("loginByMobile")
    public void loginByMobile(HttpServletResponse response,HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String token = request.getParameter("token");
        String channel = request.getParameter("channel");
        //imei->android_id->serialNunber ->UUID生成的
        String deviceSerialNo = request.getParameter("deviceSerialNo");
        //Android或IOS或H5
        String deviceType = request.getParameter("deviceType");
        String mobile = request.getParameter("mobile");
        String verifyCode = request.getParameter("verifyCode");
        if(StringUtils.isBlank(mobile) || StringUtils.isBlank(verifyCode)
                || StringUtils.isBlank(deviceType) || StringUtils.isBlank(deviceSerialNo)){
            logger.error("UserController_loginByMobile:mobile或verifyCode或deviceType或deviceSerialNo为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            //校验验证码
            Map<String,String> result = UserUtils.verifyCode(mobile, verifyCode);
            if("200".equals(result.get("code"))){
                String currentUid = "";
                User user = this.userService.findUniqueByParams("tel",mobile);
                if(StringUtils.isNotBlank(token)){
                    //用户当前已经登录
                    currentUid = UserUtils.getUserIdByToken(token);
                    if(user != null){
                        //该手机号已经被用户绑定
                        if(String.valueOf(user.getUserId()).equals(currentUid)){
                            //当前用户和绑定手机号的用户相同
                            sender.put("code",0);
                        }else{
                            //当前用户和绑定手机号的用户不同
                            sender.put("code",1);
                            sender.put("nToken",UserUtils.createToken(String.valueOf(user.getUserId())));
                            UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",currentUid);
                            if(userAccount.getMoney() > 0 || userAccount.getVirtualMoney() > 0){
                                sender.put("moneyFlag",true);
                            }else{
                                sender.put("moneyFlag",false);
                            }
                        }
                    }else{
                        //该手机号没有被用户绑定过
                        sender.put("code",0);
                        user = this.userService.getUserByUserId(Long.parseLong(currentUid));
                        user.setTel(mobile);
                        userService.update(user);
                        //清除用户缓存
                        masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
                        //设置用户第三方账号绑定状态
                        this.userReceiveService.userThirdBind(user.getUserId(),1);
                    }
                }else{
                    //用户当前未登录
                    sender.put("code",2);
                    if(user != null){
                        sender.put("token",UserUtils.createToken(String.valueOf(user.getUserId())));
                        sender.put("user",user);
                    }else{
                        //自动注册
                        user = userService.register(channel,deviceType,deviceSerialNo,request);
                        Long userId = user.getUserId();
                        user.setTel(mobile);
                        userService.update(user);
                        //清除用户缓存
                        masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
                        //设置用户第三方账号绑定状态
                        this.userReceiveService.userThirdBind(user.getUserId(),1);

                        sender.put("token",UserUtils.createToken(String.valueOf(userId)));
                        sender.put("user",user);
                    }
                }
            }else{
                sender.put("code", -1);
            }
            sender.put("message", result.get("msg"));
            sender.send(response);
        }catch (Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * QQ登录
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("loginByQQ")
    public void loginByQQ(HttpServletResponse response,HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String token = request.getParameter("token");
        String channel = request.getParameter("channel");
        //imei->android_id->serialNunber ->UUID生成的
        String deviceSerialNo = request.getParameter("deviceSerialNo");
        //Android或IOS或H5
        String deviceType = request.getParameter("deviceType");
        String openID = request.getParameter("openID");
        String json = request.getParameter("json");
        logger.info("json:" + json);
        if(StringUtils.isBlank(deviceType) || StringUtils.isBlank(deviceSerialNo)
                || StringUtils.isBlank(openID) || StringUtils.isBlank(json)){
            logger.error("UserController_register:deviceType或deviceSerialNo或openID或json为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try {
            JSONObject qqJson = JSON.parseObject(json);
            String currentUid = "";
            UserQq userQq = this.userQqService.findUniqueByParams("openid",openID);
            if(StringUtils.isNotBlank(token)){
                //用户当前已经登录
                currentUid = UserUtils.getUserIdByToken(token);
                if(userQq != null){
                    //该QQ号已经被用户绑定
                    if(String.valueOf(userQq.getUserId()).equals(currentUid)){
                        //当前用户和绑定QQ号的用户相同
                        sender.put("code",0);
                        User currUser = this.userService.getUserByUserId(Long.parseLong(currentUid));
                        currUser.setSex("男".equals(qqJson.getString("gender")) ? 1 : 2 );
                        currUser.setLogo(qqJson.getString("figureurl_qq_1"));
                        userService.update(currUser);
                        //清除用户缓存
                        masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + currentUid);
                    }else{
                        //当前用户和绑定QQ号的用户不同
                        User nUser = this.userService.getUserByUserId(userQq.getUserId());
                        sender.put("code",1);
                        sender.put("nUser",nUser);
                        sender.put("nToken",UserUtils.createToken(String.valueOf(userQq.getUserId())));
                        UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",currentUid);
                        if(userAccount.getMoney() > 0 || userAccount.getVirtualMoney() > 0){
                            sender.put("moneyFlag",true);
                        }else{
                            sender.put("moneyFlag",false);
                        }
                    }
                }else{
                    //该QQ号没有被用户绑定过
                    //修改用户头像
                    User user = this.userService.getUserByUserId(Long.parseLong(currentUid));
                    user.setSex("男".equals(qqJson.getString("gender")) ? 1 : 2 );
                    user.setLogo(qqJson.getString("figureurl_qq_1"));
                    userService.update(user);
                    //删除已绑定的QQ
                    userQqService.deleteByByParams("userId",user.getUserId());
                    //保存QQ相关信息
                    userQq = userQqService.saveUserQq(qqJson, openID, user.getUserId());
                    //清除用户缓存
                    masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
                    //设置用户第三方账号绑定状态
                    this.userReceiveService.userThirdBind(user.getUserId(),2);
                    sender.put("code",0);
                }
            }else{
                //用户当前未登录
                sender.put("code",2);
                User user = null;
                if(userQq != null){
                    user = this.userService.getUserByUserId(userQq.getUserId());
                    user.setSex("男".equals(qqJson.getString("gender")) ? 1 : 2 );
                    user.setLogo(qqJson.getString("figureurl_qq_1"));
                    userService.update(user);
                    //清除用户缓存
                    masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
                    sender.put("token",UserUtils.createToken(String.valueOf(userQq.getUserId())));
                    sender.put("user",user);
                }else{
                    //自动注册
                    user = userService.register(channel,deviceType,deviceSerialNo,request);
                    Long userId = user.getUserId();
                    user.setSex("男".equals(qqJson.getString("gender")) ? 1 : 2 );
                    user.setLogo(qqJson.getString("figureurl_qq_1"));
                    userService.update(user);
                    //保存QQ相关信息
                    userQq = userQqService.saveUserQq(qqJson, openID, userId);
                    //清除用户缓存
                    masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
                    //设置用户第三方账号绑定状态
                    this.userReceiveService.userThirdBind(user.getUserId(),2);

                    sender.put("token",UserUtils.createToken(String.valueOf(userId)));
                    sender.put("user",user);
                }
            }
            sender.success(response);
        } catch (Exception e) {
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    @RequestMapping("loginByWx")
    public void loginByWx(HttpServletResponse response,HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String token = request.getParameter("token");
        String channel = request.getParameter("channel");
        //imei->android_id->serialNunber ->UUID生成的
        String deviceSerialNo = request.getParameter("deviceSerialNo");
        //Android或IOS或H5
        String deviceType = request.getParameter("deviceType");
        String json = request.getParameter("json");
        logger.info("weixin_json:" + json);
        if(StringUtils.isBlank(deviceType) || StringUtils.isBlank(deviceSerialNo) || StringUtils.isBlank(json)){
            logger.error("UserController_loginByWx:deviceType或deviceSerialNo或json为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try {
            UserWeixin userWeixin = JSON.parseObject(json,UserWeixin.class);
            String currentUid = "";
            UserWeixin userWx = this.userWeixinService.findUniqueByParams("unionid",userWeixin.getUnionid());
            if(StringUtils.isNotBlank(token)){
                //用户当前已经登录
                currentUid = UserUtils.getUserIdByToken(token);
                if(userWx != null){
                    //该QQ号已经被用户绑定
                    if(String.valueOf(userWx.getUserId()).equals(currentUid)){
                        //当前用户和绑定微信号的用户相同
                        sender.put("code",0);
                        User currUser = this.userService.getUserByUserId(Long.parseLong(currentUid));
                        currUser.setSex(userWeixin.getSex());
                        currUser.setLogo(userWeixin.getHeadimgurl());
                        userService.update(currUser);
                        //清除用户缓存
                        masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + currUser.getUserId());
                    }else{
                        //当前用户和绑定微信号的用户不同
                        User nUser = this.userService.getUserByUserId(userWx.getUserId());
                        sender.put("code",1);
                        sender.put("nUser",nUser);
                        sender.put("nToken",UserUtils.createToken(String.valueOf(userWx.getUserId())));
                        UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",currentUid);
                        if(userAccount.getMoney() > 0 || userAccount.getVirtualMoney() > 0){
                            sender.put("moneyFlag",true);
                        }else{
                            sender.put("moneyFlag",false);
                        }
                    }
                }else{
                    //该微信号没有被用户绑定过
                    //修改用户头像
                    User user = this.userService.getUserByUserId(Long.parseLong(currentUid));
                    user.setSex(userWeixin.getSex());
                    user.setLogo(userWeixin.getHeadimgurl());
                    userService.update(user);
                    //删除已绑定的微信
                    userWeixinService.deleteByByParams("userId",user.getUserId());
                    //保存微信相关信息
                    userWeixin.setUserId(Long.parseLong(currentUid));
                    userWeixin.setUpdateDate(new Date());
                    userWeixin.setCreateDate(new Date());
                    userWeixinService.save(userWeixin);
                    //清除用户缓存
                    masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
                    //设置用户第三方账号绑定状态
                    this.userReceiveService.userThirdBind(user.getUserId(),3);
                    sender.put("code",0);
                }
            }else{
                //用户当前未登录
                sender.put("code",2);
                User user = null;
                if(userWeixin != null){
                    user = this.userService.getUserByUserId(userWeixin.getUserId());
                    user.setSex(userWeixin.getSex());
                    user.setLogo(userWeixin.getHeadimgurl());
                    userService.update(user);
                    //清除用户缓存
                    masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());

                    sender.put("token",UserUtils.createToken(String.valueOf(userWeixin.getUserId())));
                    sender.put("user",user);
                }else{
                    //自动注册
                    user = userService.register(channel,deviceType,deviceSerialNo,request);
                    Long userId = user.getUserId();
                    user.setSex(userWeixin.getSex());
                    user.setLogo(userWeixin.getHeadimgurl());
                    userService.update(user);
                    //保存微信相关信息
                    userWeixin.setUserId(userId);
                    userWeixin.setUpdateDate(new Date());
                    userWeixin.setCreateDate(new Date());
                    userWeixinService.save(userWeixin);
                    //清除用户缓存
                    masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
                    //设置用户第三方账号绑定状态
                    this.userReceiveService.userThirdBind(user.getUserId(),3);

                    sender.put("token",UserUtils.createToken(String.valueOf(userId)));
                    sender.put("user",user);
                }
            }
            sender.success(response);
        } catch (Exception e) {
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 微博登录
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("loginByWeibo")
    public void loginByWeibo(HttpServletResponse response,HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String token = request.getParameter("token");
        String channel = request.getParameter("channel");
        //imei->android_id->serialNunber ->UUID生成的
        String deviceSerialNo = request.getParameter("deviceSerialNo");
        //Android或IOS或H5
        String deviceType = request.getParameter("deviceType");
        String json = request.getParameter("json");
        logger.info("weibo_json:" + json);
        if(StringUtils.isBlank(deviceType) || StringUtils.isBlank(deviceSerialNo) || StringUtils.isBlank(json)){
            logger.error("UserController_loginByWeibo:deviceType或deviceSerialNo或json为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }

        try {
            UserWb userWb = JSON.parseObject(json,UserWb.class);
            String currentUid = "";
            UserWeibo userWeibo = this.userWeiboService.findUniqueByParams("weiboId",userWb.getId());
            if(StringUtils.isNotBlank(token)){
                //用户当前已经登录
                currentUid = UserUtils.getUserIdByToken(token);
                if(userWeibo != null){
                    //该微博号已经被用户绑定
                    if(String.valueOf(userWeibo.getUserId()).equals(currentUid)){
                        //当前用户和绑定微博号的用户相同
                        sender.put("code",0);
                        User currUser = this.userService.getUserByUserId(Long.parseLong(currentUid));
                        currUser.setSex("m".equals(userWb.getGender()) ? 1 : 2);
                        currUser.setLogo(userWb.getProfile_image_url());
                        userService.update(currUser);
                        //清除用户缓存
                        masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + currUser.getUserId());
                    }else{
                        //当前用户和绑定微博号的用户不同
                        User nUser = this.userService.getUserByUserId(userWeibo.getUserId());
                        sender.put("code",1);
                        sender.put("nUser",nUser);
                        sender.put("nToken",UserUtils.createToken(String.valueOf(userWeibo.getUserId())));
                        UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",currentUid);
                        if(userAccount.getMoney() > 0 || userAccount.getVirtualMoney() > 0){
                            sender.put("moneyFlag",true);
                        }else{
                            sender.put("moneyFlag",false);
                        }
                    }
                }else{
                    //该微博号没有被用户绑定过
                    //修改用户头像
                    User user = this.userService.getUserByUserId(Long.parseLong(currentUid));
                    user.setSex("m".equals(userWb.getGender()) ? 1 : 2);
                    user.setLogo(userWb.getProfile_image_url());
                    userService.update(user);
                    //删除已绑定的微博
                    userWeiboService.deleteByByParams("userId",user.getUserId());
                    //保存微博相关信息
                    userWeibo = userWeiboService.saveUserWeibo(userWb, user.getUserId());
                    //清除用户缓存
                    masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
                    //设置用户第三方账号绑定状态
                    this.userReceiveService.userThirdBind(user.getUserId(),4);
                    sender.put("code",0);
                }
            }else{
                //用户当前未登录
                sender.put("code",2);
                User user = null;
                if(userWeibo != null){
                    user = this.userService.getUserByUserId(userWeibo.getUserId());
                    user.setSex("m".equals(userWb.getGender()) ? 1 : 2);
                    user.setLogo(userWb.getProfile_image_url());
                    userService.update(user);
                    //清除用户缓存
                    masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());

                    sender.put("token",UserUtils.createToken(String.valueOf(userWeibo.getUserId())));
                    sender.put("user",user);
                }else{
                    //自动注册
                    user = userService.register(channel,deviceType,deviceSerialNo,request);
                    Long userId = user.getUserId();
                    user.setSex("m".equals(userWb.getGender()) ? 1 : 2);
                    user.setLogo(userWb.getProfile_image_url());
                    userService.update(user);
                    //保存微博相关信息
                    userWeibo = userWeiboService.saveUserWeibo(userWb, user.getUserId());
                    //清除用户缓存
                    masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
                    //设置用户第三方账号绑定状态
                    this.userReceiveService.userThirdBind(user.getUserId(),4);

                    sender.put("token",UserUtils.createToken(String.valueOf(userId)));
                    sender.put("user",user);
                }
            }
            sender.success(response);
        } catch (Exception e) {
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }



    /**
     * 用户注册
     * @param response
     * @param request
     */
    @RequestMapping("register")
    public void register(HttpServletResponse response,HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String channel = request.getParameter("channel");
        //imei->android_id->serialNunber ->UUID生成的
        String deviceSerialNo = request.getParameter("deviceSerialNo");
        //Android或IOS或H5
        String deviceType = request.getParameter("deviceType");
        if(StringUtils.isBlank(deviceType) || StringUtils.isBlank(deviceSerialNo)){
            logger.error("UserController_register:deviceType或deviceSerialNo为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            //根据用户imei->android_id->serialNunber ->UUID生成的查询用户
            User user =  userService.findUniqueByParams("deviceSerialNo",deviceSerialNo.toLowerCase());

            if(user == null){
                user = this.userService.register(channel,deviceType,deviceSerialNo,request);
            }
            sender.put("user",user);
            sender.put("token",UserUtils.createToken(String.valueOf(user.getUserId())));
            sender.send(response);
        }catch (Exception e){
            logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 根据userId或nickName条件查询唯一的用户
     * @param response
     * @param request
     */
    @RequestMapping("findUserByCondition")
    public void findUserByCondition(HttpServletResponse response,HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String token = request.getParameter("token");
        String nickName = request.getParameter("nickName");
        String channel = request.getParameter("channel");

        if(StringUtils.isBlank(token) && StringUtils.isBlank(nickName)){
            logger.error("UserController_findUserByCondition:token和nickName为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }

        try{
            User user = null;
            if(StringUtils.isNotBlank(token)){
                String userId = UserUtils.getUserIdByToken(token);
                if(StringUtils.isBlank(userId)){
                    logger.error("UserController_findUserByCondition:token错误");
                    sender.fail(ErrorCodeEnum.ERROR_CODE_10009.getErrorCode(),
                            ErrorCodeEnum.ERROR_CODE_10009.getErrorMessage(), response);
                    return;
                }else{
                    user = userService.getUserByUserId(Long.parseLong(userId));
                }
            }
            if(StringUtils.isNotBlank(nickName) && user == null){
                user = userService.findUniqueByParams("nickName",nickName);
            }
            if(user != null){
                if(StringUtils.isNotBlank(channel) && !channel.equals(user.getChannelNow())){
                    //如果传入的渠道和用户表中当前渠道不一致，修改用户当前渠道
                    user.setChannelNow(Integer.parseInt(channel));
                    userService.update(user);
                }
                UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",user.getUserId());
                sender.put("user",user);
                sender.put("userAccount",userAccount);
                sender.send(response);
            }else{
                sender.fail(ErrorCodeEnum.ERROR_CODE_10007.getErrorCode(),
                        ErrorCodeEnum.ERROR_CODE_10007.getErrorMessage(), response);
            }
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 获取侧边栏信息
     * @param response
     * @param request
     */
    @RequestMapping("getSidebarInfo")
    public void getSidebarInfo(HttpServletResponse response,HttpServletRequest request){
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String token = request.getParameter("token");
        String channel = request.getParameter("channel");
        String version = request.getParameter("version");
        if(StringUtils.isBlank(token) || StringUtils.isBlank(version) || StringUtils.isBlank(channel)){
            logger.error("UserController_getSidebarInfo:token或version或者channel为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        String userId = UserUtils.getUserIdByToken(token);
        if(StringUtils.isBlank(userId)){
            logger.error("UserController_getSidebarInfo:token错误");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10009.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10009.getErrorMessage(), response);
            return;
        }
        try{
            User user = userService.getUserByUserId(Long.parseLong(userId));
            Integer maxVirtual = rechargeItemService.getMaxVirtual();
            if(maxVirtual != null && maxVirtual > 0){
                sender.put("rechargeContent","充值最高返"+maxVirtual);
            }
            UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);

            VersionInfo versionInfo = this.versionInfoService.getVersionInfoByChannel(Integer.parseInt(channel));
            sender.put("versionStatus",0);
            if(versionInfo != null){
                if(versionInfo.getType() == 1){
                    //强制更新
                    sender.put("versionStatus",1);
                }
                if(versionInfo.getVersion() > Integer.parseInt(version.replace(".",""))){
                    //更新
                    sender.put("versionStatus",1);
                }
            }
            BookExpand bookExpand = this.bookExpandService.getMaxClickBook();
            if(bookExpand != null){
                sender.put("searchBook",bookExpand.getBookName());
            }
            UserReceive userReceive = this.userReceiveService.findUniqueByParams("userId",userId);
            if(userReceive != null){
                sender.put("qqStatus", userReceive.getQqStatus() != null && userReceive.getQqStatus() == 1 ? 1 : 0);
                sender.put("weiboStatus", userReceive.getWeiboStatus() != null && userReceive.getWeiboStatus() == 1 ? 1 : 0);
                sender.put("weixinStatus", userReceive.getWeixinStatus() != null && userReceive.getWeixinStatus() == 1 ? 1 : 0);
            }else{
                sender.put("qqStatus", 0);
                sender.put("weiboStatus",0);
                sender.put("weixinStatus",0);
            }
           sender.put("user",user);
           sender.put("userAccount",userAccount);
           sender.success(response);
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 检查更新
     * @param response
     * @param request
     */
    @RequestMapping("checkVersion")
    public void checkVersion(HttpServletResponse response,HttpServletRequest request){
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String channel = request.getParameter("channel");
        String version = request.getParameter("version");
        if(StringUtils.isBlank(channel)|| StringUtils.isBlank(version)){
            logger.error("UserController_checkVersion:channel或者version为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            VersionInfo versionInfo = this.versionInfoService.getVersionInfoByChannel(Integer.parseInt(channel));
            sender.put("versionStatus",0);
            if(versionInfo != null){
                if(versionInfo.getType() == 1){
                    //强制更新
                    sender.put("versionStatus",1);
                }
                if(versionInfo.getVersion() > Integer.parseInt(version.replace(".",""))){
                    //更新
                    sender.put("versionStatus",1);
                }
            }
            sender.success(response);
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }

    }

    /**
     * 修改用户昵称
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("toUpdateNickName")
    public String toUpdateNickName(HttpServletResponse response,HttpServletRequest request,Model model){
        //入参
        String token = request.getParameter("token");
        String userId = UserUtils.getUserIdByToken(token);
        try{
            if(StringUtils.isBlank(userId)){
                logger.error("UserController_toUpdateNickName:userId为空");
                response.sendRedirect("/user/toLogin.go");
                return null;
            }
            User user = this.userService.getUserByUserId(Long.parseLong(userId));
            model.addAttribute("user",user);
            return "/ucenter/toUpdateNickName";
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 修改用户昵称nickName
     * @param response
     * @param request
     */
    @RequestMapping("updateNickNameByUid")
    public void updateNickNameByUid(HttpServletResponse response,HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String token = request.getParameter("token");
        String nickName = request.getParameter("nickName");

        if(StringUtils.isBlank(token) || StringUtils.isBlank(nickName)){
            logger.error("UserController_updateNickNameByUid：token或者nickName为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        String userId = UserUtils.getUserIdByToken(token);
        if(StringUtils.isBlank(userId)){
            logger.error("UserController_updateNickNameByUid:token错误");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10009.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10009.getErrorMessage(), response);
            return;
        }
        try{
            logger.info("修改昵称为："+nickName);
            if(StringUtils.isBlank(nickName)){
                sender.fail(-1, "昵称不能为空", response);
            }else{
                //过滤表情符号
                nickName = nickName.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
                if(StringUtils.isBlank(nickName)){
                    sender.fail(-1, "昵称不能为空", response);
                }else{
                    if(nickName.length() > 8){
                        sender.fail(-1, "昵称不能超过8个字", response);
                    }else{
                        User user = userService.findUniqueByParams("nickName",nickName);
                        if(user != null && user.getUserId() != Long.parseLong(userId)){
                            sender.fail(-1, "昵称已存在", response);
                        }else{
                            user = userService.getUserByUserId(Long.parseLong(userId));
                            user.setNickName(nickName);
                            userService.update(user);
                            //清除用户缓存
                            masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + userId);
                            sender.put("user", user);
                            sender.send(response);
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 查询用户收入和消费记录
     * @param response
     * @param request
     */
    @RequestMapping("findUserAccountLog")
    public String findUserAccountLog(HttpServletResponse response,HttpServletRequest request,Model model) {
        //入参
        String token = request.getParameter("token");
        String page = request.getParameter("page");
        //1：查询收入  2：查询书籍消费  3：查询其他消费（例如:购买VIP） 4:查询所有消费
        String type = request.getParameter("type");
        String syn = request.getParameter("syn")==null?"0":request.getParameter("syn");


        if(StringUtils.isBlank(token) || StringUtils.isBlank(type)){
            logger.error("UserController_findUserRechargeLog：token或type为空");
            return "error";
        }

        String userId = UserUtils.getUserIdByToken(token);
        if(StringUtils.isBlank(userId)){
            logger.error("UserController_findUserRechargeLog:token错误");
            return "error";
        }

        model.addAttribute("type",type);
        model.addAttribute("syn",syn);
        model.addAttribute("token",token);
        try{
            if("1".equals(type) || "3".equals(type)){
                Query query = new Query();
                query.setPage(StringUtils.isNotBlank(page) ? Integer.parseInt(page) : 1);
                query.setPageSize(20);
                UserAccountLog accountLog = new UserAccountLog();
                accountLog.setUserId(Long.parseLong(userId));
                accountLog.setFindType(Integer.parseInt(type));
                PageFinder<UserAccountLog> pageFinder = this.userAccountLogService.findPageFinderObjs(accountLog,query);
                if(pageFinder != null && CollectionUtils.isNotEmpty(pageFinder.getData())){
                    model.addAttribute("pageFinder",pageFinder);
                }else{
                    return "/ucenter/account_no_log";
                }
            }else if("2".equals(type)){
                UserAccountLog accountLogCondition = new UserAccountLog();
                accountLogCondition.setUserId(Long.parseLong(userId));
                accountLogCondition.setFindType(Integer.parseInt(type));
                List<UserAccountLog> accountLogs = this.userAccountLogService.findListByParamsObjs(accountLogCondition);
                if(CollectionUtils.isNotEmpty(accountLogs)){
                    List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
                    for (UserAccountLog accountLog : accountLogs) {
                        Map<String,Object> map = new HashMap<String, Object>();
                        map.put("charge",accountLog.getUnitMoney()+accountLog.getUnitVirtual());
                        map.put("createDate", DateUtil.formatDateByFormat(accountLog.getCreateDate(),DateUtil.DATE_PATTERN));
                        map.put("bookId",accountLog.getProductId());
                        map.put("type",accountLog.getType());
                        //是否存在标识
                        int flag=0;
                        for (Map<String,Object> map1 : result) {
                            if(map1.get("bookId").equals(map.get("bookId"))){
                                map1.put("charge",Integer.parseInt(map1.get("charge").toString())+Integer.parseInt(map.get("charge").toString()));
                                map1.put("createDate", map.get("createDate"));
                                flag=1;
                                break;
                            }
                        }
                        if(flag==0){
                            Book book = this.bookService.getBookById(Long.parseLong(accountLog.getProductId()));
                            if(book != null){
                                map.put("title",book.getTitle());
                                result.add(map);
                            }
                        }
                    }
                    model.addAttribute("records",result);
                }else{
                    return "/ucenter/account_no_log";
                }
            }
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
            return "error";
        }
        //1：查询收入  2：查询书籍消费  3：查询其他消费（例如:购买VIP） 4:查询所有消费
        if("1".equals(type)){
            return "/ucenter/account_recharge_log";
        }else if("2".equals(type)){
            return "/ucenter/account_book_log";
        }else if("3".equals(type)){
            return "/ucenter/account_other_log";
        }
        return "error";
    }

    /**
     * 查询用户某一本书的购买详情
     * @param response
     * @param request
     */
    @RequestMapping("findBookAccountLog")
    public String findBookAccountLog(HttpServletResponse response,HttpServletRequest request,Model model) {
        //入参
        String token = request.getParameter("token");
        String bookId = request.getParameter("bookId");
        String page = request.getParameter("page");
        String syn = request.getParameter("syn")==null?"0":request.getParameter("syn");

        if(StringUtils.isBlank(token) || StringUtils.isBlank(bookId)){
            logger.error("UserController_findUserRechargeLog：token或bookId为空");
            return "error";
        }
        String userId = UserUtils.getUserIdByToken(token);
        if(StringUtils.isBlank(userId)){
            logger.error("UserController_findUserRechargeLog:token错误");
            return "error";
        }

        model.addAttribute("syn",syn);
        model.addAttribute("token",token);
        model.addAttribute("bookId",bookId);
        try{
            Query query = new Query();
            query.setPage(StringUtils.isNotBlank(page) ? Integer.parseInt(page) : 1);
            query.setPageSize(20);
            UserAccountLog accountLogCondition = new UserAccountLog();
            accountLogCondition.setUserId(Long.parseLong(userId));
            accountLogCondition.setProductId(bookId);
            PageFinder<UserAccountLog> pageFinder = this.userAccountLogService.findPageFinderObjs(accountLogCondition,query);

            List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
            int num = 0;
            for(UserAccountLog userAccountLog : pageFinder.getData()){
                Map<String,Object> data = new HashMap<String, Object>();
                Map<String,Object> comment = JSON.parseObject(userAccountLog.getComment());
                if(userAccountLog.getType() == -1){
                    Long chapterId = Long.parseLong(comment.get("chapterId").toString());
                    Chapter chapter = this.chapterService.getChapterById(chapterId,0,(int)Long.parseLong(bookId) % Constants.CHAPTR_TABLE_NUM);
                    data.put("name",chapter.getTitle());
                    num = num+1;
                }else if(userAccountLog.getType() == -2){
                    int count = Integer.parseInt(comment.get("num").toString());
                    num = num + count;
                    data.put("name","批量订购"+count+"章");

                }
                data.put("charge",userAccountLog.getUnitMoney() + userAccountLog.getUnitVirtual());
                data.put("createDate",userAccountLog.getCreateDate());
                result.add(data);
            }

            model.addAttribute("num",num);
            model.addAttribute("data",result);
            model.addAttribute("pageNo",pageFinder.getPageNo());
            model.addAttribute("pageCount",pageFinder.getPageCount());
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
            return "error";
        }
        return "/ucenter/account_book_detail";
    }

    /**
     * 获取新手礼包信息
     * @param response
     * @param request
     */
    @RequestMapping("getNewUserVipInfo")
    public void getNewUserVipInfo(HttpServletResponse response,HttpServletRequest request){
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String token = request.getParameter("token");
        if(StringUtils.isBlank(token)){
            logger.error("UserController_getNewUserVipInfo：token为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        String userId = UserUtils.getUserIdByToken(token);
        if(StringUtils.isBlank(userId)){
            logger.error("UserController_getNewUserVipInfo:token错误");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10009.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10009.getErrorMessage(), response);
            return;
        }
        try{
            UserReceive userReceive = this.userReceiveService.findUniqueByParams("userId",userId);
            if(userReceive != null && userReceive.getVipStatus() == 1){
                sender.put("receiveStatus",1);
            }else{
                sender.put("receiveStatus",0);
                //新手礼包天数3
                sender.put("days",3);
            }
            sender.success(response);
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 领取新手礼包信息
     * @param response
     * @param request
     */
    @RequestMapping("receiveNewUserVip")
    public void receiveNewUserVip(HttpServletResponse response,HttpServletRequest request){
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String token = request.getParameter("token");
        String days = request.getParameter("days");
        String channel = request.getParameter("channel");
        if(StringUtils.isBlank(token)){
            logger.error("UserController_getNewUserVipInfo：token或days为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        String userId = UserUtils.getUserIdByToken(token);
        if(StringUtils.isBlank(userId)){
            logger.error("UserController_getNewUserVipInfo:token错误");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10009.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10009.getErrorMessage(), response);
            return;
        }
        try{
            UserVip userVip = this.userVipService.findUniqueByParams("userId",userId);
            Calendar calendar = Calendar.getInstance();
            if(userVip != null){
                Date now = new Date();
                if(now.getTime() > userVip.getEndDate().getTime()){
                    calendar.setTime(now);
                }else{
                    calendar.setTime(userVip.getEndDate());
                }
                calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(days));
                userVip.setEndDate(calendar.getTime());
                userVip.setUpdateDate(new Date());
                this.userVipService.update(userVip);
            }else{
                userVip = new UserVip();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(days));
                userVip.setEndDate(calendar.getTime());
                userVip.setUserId(Long.parseLong(userId));
                userVip.setUpdateDate(new Date());
                userVip.setCreateDate(new Date());
                if(StringUtils.isNotBlank(channel)){
                    userVip.setChannel(Integer.parseInt(channel));
                }
                userVipService.save(userVip);
            }

            UserReceive userReceive = this.userReceiveService.findUniqueByParams("userId",userId);
            if(userReceive != null){
                userReceive.setVipStatus(1);
                userReceive.setUpdateDate(new Date());
                this.userReceiveService.update(userReceive);
            }else{
                userReceive = new UserReceive();
                userReceive.setUserId(Long.parseLong(userId));
                userReceive.setVipStatus(1);
                userReceive.setUpdateDate(new Date());
                userReceive.setCreateDate(new Date());
                this.userReceiveService.save(userReceive);
            }
            //清除用户缓存
            masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + userId);
            sender.success(response);
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 取消领取VIP新手礼包 第三次取消自动领取
     * @param response
     * @param request
     */
    @RequestMapping("cancelNewUserVip")
    public void cancelNewUserVip(HttpServletResponse response,HttpServletRequest request){
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String token = request.getParameter("token");
        String days = request.getParameter("days");
        String channel = request.getParameter("channel");
        if(StringUtils.isBlank(token)){
            logger.error("UserController_cancelNewUserVip：token或days为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        String userId = UserUtils.getUserIdByToken(token);
        if(StringUtils.isBlank(userId)){
            logger.error("UserController_cancelNewUserVip:token错误");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10009.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10009.getErrorMessage(), response);
            return;
        }
        try{
            sender.put("type",0);
            sender.put("msg","取消成功");
            UserReceive userReceive = this.userReceiveService.findUniqueByParams("userId",userId);
            if(userReceive != null){
                userReceive.setCancelVipTimes((userReceive.getCancelVipTimes() == null ? 0 : userReceive.getCancelVipTimes()) +1);
                userReceive.setUpdateDate(new Date());
                if(userReceive.getCancelVipTimes() >= 3
                        && (userReceive.getVipStatus() == null || userReceive.getVipStatus() == 0)){
                    //三次取消自动领取
                    UserVip userVip = this.userVipService.findUniqueByParams("userId",userId);
                    Calendar calendar = Calendar.getInstance();
                    if(userVip != null){
                        calendar.setTime(userVip.getEndDate());
                        calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(days));
                        userVip.setEndDate(calendar.getTime());
                        userVip.setUpdateDate(new Date());
                        this.userVipService.update(userVip);
                    }else{
                        userVip = new UserVip();
                        calendar.setTime(new Date());
                        calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(days));
                        userVip.setEndDate(calendar.getTime());
                        userVip.setUserId(Long.parseLong(userId));
                        userVip.setUpdateDate(new Date());
                        userVip.setCreateDate(new Date());
                        if(StringUtils.isNotBlank(channel)){
                            userVip.setChannel(Integer.parseInt(channel));
                        }
                        userVipService.save(userVip);
                    }
                    userReceive.setVipStatus(1);
                    sender.put("type",1);
                    sender.put("msg","已成功领取3天全站免费特权");
                    //清除用户缓存
                    masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + userId);
                }
                this.userReceiveService.update(userReceive);
            }else{
                userReceive = new UserReceive();
                userReceive.setUserId(Long.parseLong(userId));
                userReceive.setVipStatus(0);
                userReceive.setCancelVipTimes(1);
                userReceive.setUpdateDate(new Date());
                userReceive.setCreateDate(new Date());
                this.userReceiveService.save(userReceive);
            }
            sender.success(response);
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

}
