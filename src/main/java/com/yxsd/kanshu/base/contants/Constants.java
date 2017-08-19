package com.yxsd.kanshu.base.contants;

import java.util.regex.Pattern;


/**
 * @author hushengmeng
 * @date 2017/7/5.
 */
public class Constants {
    public static final String CMS_USER_INFO_STORED_IN_SESSION = "userSessionInfo";
    public static final String CMS_ERROR_MESSAGE = "cms_error_message";
    public static final String AJAX_REQUEST_RESULT_FAILURE = "failure";
    public static final String AJAX_REQUEST_RESULT_SUCCESS = "success";
    public static final String DEFAULT_PASSWORD = "dang@dang";
    public static Pattern commaSpliter = Pattern.compile(",");
    public static Pattern semiColonSpliter = Pattern.compile(";");
    public static final String ALL_FUNCTIONALITIES_SYSTEM = "all_functionalities_system";
    public static final String ALL_FUNCTIONALITIES_USER = "all_functionalities_user";

    public static final int CHAPTR_TABLE_NUM = 10;//章节表数量

    public static final int CONSUME_TYPE_1 = 1;//支付宝充值
    public static final int CONSUME_TYPE_2 = 2;//微信充值
    public static final int CONSUME_TYPE_S1 = -1;//单章购买
    public static final int CONSUME_TYPE_S2 = -2;//批量购买
    public static final int CONSUME_TYPE_S3 = -3;//全本购买
    public static final int CONSUME_TYPE_S4 = -4;//VIP消费

    public Constants() {
    }
}
