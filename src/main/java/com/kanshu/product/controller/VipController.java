package com.kanshu.product.controller;

import com.kanshu.base.controller.BaseController;
import com.kanshu.product.model.Vip;
import com.kanshu.product.service.IVipService;
import com.kanshu.ucenter.model.User;
import com.kanshu.ucenter.model.UserAccount;
import com.kanshu.ucenter.service.IUserAccountService;
import com.kanshu.ucenter.service.IUserService;
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
import java.util.List;

/**
 * Created by lenovo on 2017/8/7.
 */
@Controller
@Scope("prototype")
@RequestMapping("vip")
public class VipController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(VipController.class);

    @Resource(name="vipService")
    IVipService vipService;

    @Resource(name="userService")
    IUserService userService;

    @Resource(name="userAccountService")
    IUserAccountService userAccountService;

    @RequestMapping("index")
    public String index(HttpServletResponse response, HttpServletRequest request,Model model) {
        String userId = request.getParameter("userId");
        try {
            if(StringUtils.isBlank(userId)){
                logger.error("VipController_index:userId为空");
                response.sendRedirect("/user/login.go");
                return null;
            }

            User user = userService.getUserByUserId(Long.parseLong(userId));
            UserAccount userAccount = this.userAccountService.findUniqueByParams("userId",userId);
            if(user != null){
                List<Vip> vips = this.vipService.findListByParams();
                model.addAttribute("vips",vips);
                model.addAttribute("user",user);
                model.addAttribute("userAccount",userAccount);
            }else{
                logger.error("VipController_index:根据userId未查到用户");
                response.sendRedirect("/user/login.go");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/product/vip_index";
    }

}
