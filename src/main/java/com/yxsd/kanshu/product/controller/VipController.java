package com.yxsd.kanshu.product.controller;

import com.yxsd.kanshu.base.controller.BaseController;
import com.yxsd.kanshu.base.utils.UserUtils;
import com.yxsd.kanshu.product.model.Vip;
import com.yxsd.kanshu.product.service.IVipService;
import com.yxsd.kanshu.ucenter.model.User;
import com.yxsd.kanshu.ucenter.model.UserAccount;
import com.yxsd.kanshu.ucenter.service.IUserAccountService;
import com.yxsd.kanshu.ucenter.service.IUserService;
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
        String token = request.getParameter("token");
        try {
            if(StringUtils.isBlank(token)){
                logger.error("VipController_index:token为空");
                return "error";
            }
            String userId = UserUtils.getUserIdByToken(token);
            if(StringUtils.isBlank(userId)){
                logger.error("VipController_index:token错误");
                return "error";
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
