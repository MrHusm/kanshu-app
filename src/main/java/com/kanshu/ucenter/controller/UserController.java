package com.kanshu.ucenter.controller;

import com.kanshu.base.contants.ErrorCodeEnum;
import com.kanshu.base.controller.BaseController;
import com.kanshu.base.utils.JsonResultSender;
import com.kanshu.base.utils.ResultSender;
import com.kanshu.ucenter.model.User;
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
import java.util.Date;
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

    @RequestMapping("index")
    public String loginSubmit(Model model, User user, String captcha) {
        User u = userService.findMasterById(1L);
        return "main";
    }

    @RequestMapping("register")
    public void register(HttpServletResponse response,HttpServletRequest request) {
        ResultSender sender = JsonResultSender.getInstance();
        //客户端生成的唯一标识
        String key = request.getParameter("key");
        //0:安卓 1：ios
        String type = request.getParameter("type");
        if(StringUtils.isBlank(key)){
            sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
                    ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
            return;
        }
        try{
            User user = userService.findUniqueByParams("key",key);
            if(user == null){
                user = new User();
                user.setCreateDate(new Date());
                user.setName(UUID.randomUUID().toString().substring(0,8));
                user.setNickName(user.getName());
                user.setPassword("111111");
                user.setUpdateDate(new Date());
                this.userService.save(user);
            }
            sender.put("user",user);
            sender.send(response);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("系统错误："+ request.getRequestURL());
            sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
        }
    }
}
