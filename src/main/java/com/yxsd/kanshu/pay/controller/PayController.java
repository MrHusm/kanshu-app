package com.yxsd.kanshu.pay.controller;

import com.yxsd.kanshu.base.contants.ErrorCodeEnum;
import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.JsonResultSender;
import com.yxsd.kanshu.base.utils.ResultSender;
import com.yxsd.kanshu.base.utils.UserUtils;
import com.yxsd.kanshu.pay.model.RechargeItem;
import com.yxsd.kanshu.pay.service.IRechargeItemService;
import com.yxsd.kanshu.ucenter.model.UserAccount;
import com.yxsd.kanshu.ucenter.model.UserAccountLog;
import com.yxsd.kanshu.ucenter.service.IUserAccountLogService;
import com.yxsd.kanshu.ucenter.service.IUserAccountService;
import com.yxsd.kanshu.ucenter.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/8/6.
 */
@Controller
@Scope("prototype")
@RequestMapping("pay")
public class PayController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @Resource(name="rechargeItemService")
    IRechargeItemService rechargeItemService;

    @Resource(name="userAccountService")
    IUserAccountService userAccountService;

    @Resource(name="userAccountLogService")
    private IUserAccountLogService userAccountLogService;

    @Resource(name="userService")
    IUserService userService;

    /**
     * 获取充值档位信息
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("payItems")
     public void payItems(HttpServletResponse response, HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String token = request.getParameter("token");
        try {
            if (StringUtils.isBlank(token)) {
                logger.error("PayController_payItems：token为空");
                sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                        ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
                return;
            }
            UserAccount userAccount = userAccountService.findUniqueByParams("userId", UserUtils.getUserIdByToken(token));
            List<RechargeItem> rechargeItems = this.rechargeItemService.getRechargeItem(1);
            sender.put("rechargeItems", rechargeItems);
            sender.put("userAccount", userAccount);
            sender.success(response);
        } catch (Exception e) {
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }

    /**
     * 赠送充值
     * @param response
     * @param request
     */
    @RequestMapping("charge")
    public void charge(HttpServletResponse response, HttpServletRequest request){
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String userId = request.getParameter("userId");
        String channel = request.getParameter("channel");
        String type = request.getParameter("type");
        String money = request.getParameter("money");
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(type) || StringUtils.isBlank(money)){
            logger.error("PayController_charge：userId或money或type为空");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        if(Integer.parseInt(type) <= 10){
            logger.error("PayController_charge：type必须大于10");
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            //用户账户充值
            UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);
            userAccount.setVirtualMoney(userAccount.getVirtualMoney() + Integer.parseInt(money));
            userAccount.setUpdateDate(new Date());
            this.userAccountService.update(userAccount);

            //保存消费日志表
            UserAccountLog accountLog = new UserAccountLog();
            accountLog.setUserId(Long.parseLong(userId));
            accountLog.setChannel(StringUtils.isBlank(channel) ? null : Integer.parseInt(channel));
            accountLog.setOrderNo(Long.toHexString(System.currentTimeMillis()));
            accountLog.setType(Integer.parseInt(type));
            accountLog.setUnitMoney(0);
            accountLog.setUnitVirtual(Integer.parseInt(money));
            accountLog.setCreateDate(new Date());
            userAccountLogService.save(accountLog);
            sender.success(response);
        }catch (Exception e){
            logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
            e.printStackTrace();
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }
}
