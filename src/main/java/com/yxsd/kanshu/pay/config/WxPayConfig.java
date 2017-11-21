package com.yxsd.kanshu.pay.config;

/**
 * Created by lenovo on 2017/10/5.
 */
public class WxPayConfig {
    // 公众账号ID
    public static final String APPID = "wxa198592e76051714";
    //
    public static final String APP_SECRET = "6ec5cb42774bd84f9d05c70bdc5e87e0";
    // 商户号
    public static final String MCH_ID = "1492771722";
    //密钥
    public static final String KEY = "vn1GjTN7A3VGQ93cy0gq1ZnKPnpuHDop";
    // 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数
    public static final String NOTIFY_URL = "http://m.chunnuan999.com/weixin/notifyUrl.go";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static final String RETURN_URL = "http://m.chunnuan999.com/weixin/returnUrl.go";

}
