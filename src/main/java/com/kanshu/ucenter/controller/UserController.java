package com.kanshu.ucenter.controller;

import com.kanshu.base.contants.ErrorCodeEnum;
import com.kanshu.base.controller.BaseController;
import com.kanshu.base.utils.JsonResultSender;
import com.kanshu.base.utils.PageFinder;
import com.kanshu.base.utils.Query;
import com.kanshu.base.utils.ResultSender;
import com.kanshu.ucenter.model.User;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @RequestMapping("index")
    public String loginSubmit(Model model, User user, String captcha) {
        User u = userService.findMasterById(1L);
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
                user = userService.findUniqueByParams("imei",imei.toLowerCase());
            }
            if(user == null){
                UserUuid userUuid = new UserUuid();
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
            }
            sender.put("user",user);
            sender.send(response);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("系统错误："+ request.getRequestURL());
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 根据userId或nickName条件查询唯一的用户
     * @param response
     * @param request
     */
    @RequestMapping("findUniqueByCondition")
    public void findUniqueByCondition(HttpServletResponse response,HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String userId = request.getParameter("userId");
        String nickName = request.getParameter("nickName");

        if(StringUtils.isBlank(userId) && StringUtils.isBlank(nickName)){
            logger.error("UserController_findUniqueByCondition:userId和nickName为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            User user = null;
            if(StringUtils.isNotBlank(userId)){
                user = userService.findUniqueByParams("userId",userId);
            }
            if(StringUtils.isNotBlank(nickName) && user == null){
                user = userService.findUniqueByParams("nickName",nickName);
            }
            if(user != null){
                sender.put("user",user);
                sender.send(response);
            }else{
                sender.fail(ErrorCodeEnum.ERROR_CODE_10007.getErrorCode(),
                        ErrorCodeEnum.ERROR_CODE_10007.getErrorMessage(), response);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("系统错误："+ request.getRequestURL());
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
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
            sender.put("user",user);
            sender.send(response);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("系统错误："+ request.getRequestURL());
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
            e.printStackTrace();
            logger.error("系统错误："+ request.getRequestURL());
            return "error";
        }
        return "/ucenter/recharge_log";
    }
}
