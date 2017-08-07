package com.kanshu.pay.controller;

import com.kanshu.base.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2017/8/6.
 */
@Controller
@Scope("prototype")
@RequestMapping("pay")
public class PayController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @RequestMapping("index")
    public String index(HttpServletResponse response, HttpServletRequest request){
        //payService.saveUserPayBefore(getUid(),getAppUrl(),URLDecoder.decode(getCallBackUrl(), "UTF-8"),"");
        return "pay/pay_index";
    }
}
