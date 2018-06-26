package com.yxsd.kanshu.base.utils;

import com.alibaba.fastjson.JSON;
import com.yxsd.kanshu.base.contants.Constants;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lenovo on 2017/9/11.
 */
public class UserUtils {

    private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);

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
     * 校验验证码
     * @param mobile
     * @return
     */
    public static Map<String,String> verifyCode(String mobile, String verifyCode){
        Map<String,String> message = new HashMap<String,String>();
        try {
            String url = ConfigPropertieUtils.getString("tel_verify_code_url");
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String appKey = ConfigPropertieUtils.getString("tel_appKey");
            String appSecret = ConfigPropertieUtils.getString("tel_appSecret");
            String nonce =  CommonUtil.createNoncestr();
            String curTime = String.valueOf((new Date()).getTime() / 1000L);
            String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);//参考 计算CheckSum的java代码

            // 设置请求的header
            httpPost.addHeader("AppKey", appKey);
            httpPost.addHeader("Nonce", nonce);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            // 设置请求的参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("mobile", mobile));
            nvps.add(new BasicNameValuePair("code", verifyCode));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

            // 执行请求
            HttpResponse httpResponse = httpClient.execute(httpPost);

            // 打印执行结果
            String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            logger.info("校验验证码返回：" + result);
            Integer code = JSON.parseObject(result).getInteger("code");
            message.put("code",String.valueOf(code));
            if(code == 200){
                message.put("msg","校验成功");
            }else if(code == 301){
                message.put("msg","被封禁");
            }else if(code == 315){
                message.put("msg","IP限制");
            }else if(code == 403){
                message.put("msg","非法操作或没有权限");
            }else if(code == 404){
                message.put("msg","对象不存在");
            }else if(code == 413){
                message.put("msg","验证码错误");
            }else if(code == 414){
                message.put("msg","参数错误");
            }else if(code == 500){
                message.put("msg","服务器内部错误");
            }else{
                message.put("msg","其他错误");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 通用参数链接
     * @param request
     * @return
     */
//    public static String getAppUrl(HttpServletRequest request) {
//        String appUrl="channel="+request.getParameter("channel")+"&version="+request.getParameter("version")+"&deviceType="+request.getParameter("deviceType")
//                +"&deviceSerialNo="+request.getParameter("deviceSerialNo")+"&resolution="+request.getParameter("resolution")+"&clientOs="+request.getParameter("clientOs")
//                +"&macAddr="+request.getParameter("macAddr")+"&packname="+request.getParameter("packname")+"&model="+request.getParameter("model")+"&modelNo="
//                +request.getParameter("modelNo");
//        return appUrl;
//    }

    public static void main(String[] args) {
        String token = createToken("3");
        System.out.println(token);
        System.out.println(getUserIdByToken("IQCFqchHf4ZFlP*5t*7gpTyKfO*OcDut"));


    }
}
