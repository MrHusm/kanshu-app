package com.kanshu.base.contants;

import java.util.regex.Pattern;

/**
 * @author hushengmeng
 * @date 2017/7/5.
 */
public class DigitalCmsConstants {
    public static final String CMS_USER_INFO_STORED_IN_SESSION = "userSessionInfo";
    public static final String CMS_ERROR_MESSAGE = "cms_error_message";
    public static final String AJAX_REQUEST_RESULT_FAILURE = "failure";
    public static final String AJAX_REQUEST_RESULT_SUCCESS = "success";
    public static final String DEFAULT_PASSWORD = "dang@dang";
    public static Pattern commaSpliter = Pattern.compile(",");
    public static Pattern semiColonSpliter = Pattern.compile(";");
    public static final String ALL_FUNCTIONALITIES_SYSTEM = "all_functionalities_system";
    public static final String ALL_FUNCTIONALITIES_USER = "all_functionalities_user";

    public DigitalCmsConstants() {
    }
}
