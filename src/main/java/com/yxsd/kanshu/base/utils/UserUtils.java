package com.yxsd.kanshu.base.utils;

import com.yxsd.kanshu.base.contants.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lenovo on 2017/9/11.
 */
public class UserUtils {

    /**
     * 生成token
     * @param userId
     * @return
     */
    public static String createToken(String userId) {
        DES des = new DES(Constants.DES_KEY);
        return des.encrypt(userId);
    }

    /**
     * 解密token
     * @param token
     * @return
     */
    public static String getUserIdByToken(String token) {
        DES des = new DES(Constants.DES_KEY);
        return des.decrypt(token);
    }

    /**
     * 通用参数链接
     * @param request
     * @return
     */
    public static String getAppUrl(HttpServletRequest request) {
        String appUrl="channel="+request.getParameter("channel")+"&version="+request.getParameter("version")+"&deviceType="+request.getParameter("deviceType")
                +"&deviceSerialNo="+request.getParameter("deviceSerialNo")+"&resolution="+request.getParameter("resolution")+"&clientOs="+request.getParameter("clientOs")
                +"&macAddr="+request.getParameter("macAddr")+"&packname="+request.getParameter("packname")+"&model="+request.getParameter("model")+"&modelNo="
                +request.getParameter("modelNo");
        return appUrl;
    }

    public static void main(String[] args) {
        String token = createToken("857");
        System.out.println(token);
        System.out.println(getUserIdByToken(token));


    }
}
