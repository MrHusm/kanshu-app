package com.kanshu.pay.controller;

import com.kanshu.base.contants.ErrorCodeEnum;
import com.kanshu.base.controller.BaseController;
import com.kanshu.base.utils.JsonResultSender;
import com.kanshu.base.utils.ResultSender;
import com.kanshu.pay.service.IRechargeItemService;
import com.kanshu.ucenter.model.UserAccount;
import com.kanshu.ucenter.service.IUserAccountService;
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
import java.io.IOException;
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


    @RequestMapping("index")
    public String index(HttpServletResponse response, HttpServletRequest request,Model model){
        ResultSender sender = JsonResultSender.getInstance();
        //入参
        String userId = request.getParameter("userId");
        try {
            if(StringUtils.isBlank(userId)){
                logger.error("PayController_index：userId为空");
                sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                        ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
                response.sendRedirect("/user/login.go");
                return null;
            }
            UserAccount userAccount = userAccountService.findUniqueByParams("userId",userId);
            List<RechargeItem> rechargeItems = this.rechargeItemService.findListByParams();
            model.addAttribute("rechargeItems",rechargeItems);
            model.addAttribute("userAccount",userAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/pay/pay_index";
    }
}
