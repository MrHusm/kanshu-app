package com.kanshu.ucenter.controller;

import com.kanshu.base.contants.ErrorCodeEnum;
import com.kanshu.base.contants.RedisKeyConstants;
import com.kanshu.base.controller.BaseController;
import com.kanshu.base.utils.JsonResultSender;
import com.kanshu.base.utils.PageFinder;
import com.kanshu.base.utils.Query;
import com.kanshu.base.utils.ResultSender;
import com.kanshu.ucenter.model.User;
import com.kanshu.ucenter.model.UserAccount;
import com.kanshu.ucenter.model.UserAccountLog;
import com.kanshu.ucenter.model.UserUuid;
import com.kanshu.ucenter.service.IUserAccountLogService;
import com.kanshu.ucenter.service.IUserAccountService;
import com.kanshu.ucenter.service.IUserService;
import com.kanshu.ucenter.service.IUserUuidService;
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
import java.util.Date;
import java.util.List;

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

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate masterRedisTemplate;

    @RequestMapping("index")
    public String loginSubmit(Model model, User user, String captcha) {
        User u = userService.getUserByUserId(1L);
        return "main";
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
        String imsi = request.getParameter("imsi");
        String imei = request.getParameter("imei");
        String channel = request.getParameter("channel");
        //0:安卓 1：ios 2:h5
        String type = request.getParameter("type");
        if(StringUtils.isBlank(type)){
            logger.error("UserController_register:type为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            User user = null;
            //根据用户的imei号和imsi号查询用户
            if(StringUtils.isNotBlank(imei)){
                user = userService.findUniqueByParams("imei",imei.toLowerCase());
            }
            if(StringUtils.isNotBlank(imsi) && user == null){
                user = userService.findUniqueByParams("imsi",imsi.toLowerCase());
            }
            if(user == null){
                UserUuid userUuid = new UserUuid();
                userUuid.setCreateDate(new Date());
                userUuidService.save(userUuid);
                user = new User();
                user.setName("v"+userUuid.getId());
                user.setNickName("v"+userUuid.getId());
                user.setPassword("v"+Long.toHexString(System.currentTimeMillis()));
                user.setType(Integer.parseInt(type));
                if(StringUtils.isNotBlank(imei)){
                    user.setImei(imei.toLowerCase());
                }
                if(StringUtils.isNotBlank(imsi)){
                    user.setImsi(imsi.toLowerCase());
                }
                if(StringUtils.isNotBlank(channel)){
                    user.setChannel(Integer.parseInt(channel));
                }
                user.setCreateDate(new Date());
                user.setUpdateDate(new Date());
                userService.save(user);
                //保存账号相关信息
                UserAccount userAccount = new UserAccount();
                userAccount.setUserId(user.getUserId());
                userAccount.setMoney(0);
                userAccount.setVirtualMoney(0);
                userAccount.setCreateDate(new Date());
                userAccount.setUpdateDate(new Date());
                userAccountService.save(userAccount);
            }
            sender.put("user",user);
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
        String userId = request.getParameter("userId");
        String nickName = request.getParameter("nickName");

        if(StringUtils.isBlank(userId) && StringUtils.isBlank(nickName)){
            logger.error("UserController_findUserByCondition:userId和nickName为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            User user = null;
            if(StringUtils.isNotBlank(userId)){
                user = userService.getUserByUserId(Long.parseLong(userId));
            }
            if(StringUtils.isNotBlank(nickName) && user == null){
                user = userService.findUniqueByParams("nickName",nickName);
            }
            if(user != null){
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
     * 修改用户昵称
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("toUpdateNickName")
    public String toUpdateNickName(HttpServletResponse response,HttpServletRequest request,Model model){
        //入参
        String userId = request.getParameter("userId");
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
            logger.error("系统错误："+ request.getRequestURL()+request.getQueryString());
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
        String userId = request.getParameter("userId");
        String nickName = request.getParameter("nickName");

        if(StringUtils.isBlank(userId) || StringUtils.isBlank(nickName)){
            logger.error("UserController_updateNickNameByUid：userId或者nickName为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            User user = userService.findUniqueByParams("userId",userId);
            user.setNickName(nickName);
            userService.update(user);
            //清除用户缓存
            masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + userId);
            sender.put("user", user);
            sender.send(response);
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + request.getQueryString());
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
        String userId = request.getParameter("userId");
        String page = request.getParameter("page");
        //1：查询收入  2：查询书籍消费  3：查询其他消费（例如:购买VIP） 4:查询所有消费
        String type = request.getParameter("type");
        String syn = request.getParameter("syn")==null?"0":request.getParameter("syn");
        model.addAttribute("syn",syn);

        if(StringUtils.isBlank(userId)){
            logger.error("UserController_findUserRechargeLog：userId为空");
            return "error";
        }
        if(StringUtils.isBlank(type)){
            logger.error("UserController_findUserRechargeLog：type为空");
            return "error";
        }
        try{
            if("1".equals(type) || "3".equals(type)){
                Query query = new Query();
                query.setPage(StringUtils.isNotBlank(page) ? Integer.parseInt(page) : 1);
                query.setPageSize(20);
                UserAccountLog accountLog = new UserAccountLog();
                accountLog.setUserId(Long.parseLong(userId));
                accountLog.setFindType(Integer.parseInt(type));
                PageFinder<UserAccountLog> pageFinder = this.userAccountLogService.findPageFinderObjs(accountLog,query);
                if(pageFinder != null){
                    model.addAttribute("pageFinder",pageFinder);
                }
            }else if("2".equals(type)){
                UserAccountLog accountLog = new UserAccountLog();
                accountLog.setUserId(Long.parseLong(userId));
                accountLog.setFindType(Integer.parseInt(type));
                List<UserAccountLog> accountLogs = this.userAccountLogService.findListByParams(accountLog);
                if(CollectionUtils.isNotEmpty(accountLogs)){
                    //TODO
                }
            }

        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + request.getQueryString());
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
}
