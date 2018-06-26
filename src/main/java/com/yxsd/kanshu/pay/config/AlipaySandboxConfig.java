package com.yxsd.kanshu.pay.config;

/**
 * Created by lenovo on 2017/8/5.
 */
public class AlipayConfig {
    // 商户appid
    public static String APPID = "2016082600312026";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCOJLWscC9L/ErjfcOLTPu6b3+PryKnKhAst6AJswbbFkr3znbvUuN9cjNeFvXcQ3J+U4cXo2MgRlCEQKBzW0CSWk9xAAOPb8U/mtu2XAmKXF8PRo5jX6e9NJpQtzBPurz6wAuZBZZn1iZV+oiRiV8QCu8rv5ONnjM2IsoLGi7wAviI80W/HycE2sMZ06d3GeMn57xIOkwRfZGX1o7We3Xgva9MPsfssccZxML14HjB5ow9sPRVKHG7k/tlOylODSOoVueEVgJYkish8OUWCzow3dxReMi9iEu/ZkBuImNvOPq1A84FTLZeWPPz1L7unJpXA7ncVATYjKhujtwn+NbfAgMBAAECggEAbjF7PaECqY9+pvl6PGWDyLZjV4pX/OuY+nGn+Zz2NaYEnDNM9J93iGqd89Us6ILJIbGzStn5IK4iAUTwnj0hw9OLPnjaSbpGWda9eZPEOaT+dKMoA1XGfeFnuCcrpsOHeYSj+Q7h2MGlmoXMlba/IXq4i1vLEcCQxukVZKH5vUoGtxUUrJsMoA1PMnO9xAhp0KUoONY98HY3p6zWbwmJGb46qG6/JhKBRG3ICSMUHowBN+D/LTU31wYuFPBlbqH/NTlgcXBhcys1Ydt3Zt2Dd/AWwmQPn88ISd3UH/rclLrrfZ6/kcRh9WScj/Q3st7MkSRkZjGNLVPTPkUBAoTzUQKBgQDVKRQbh3t2SlwBSBcaHZMarYCVkjDchbJ4GVancR6UM699Ol11/kmPglf5cvZC1RJwIbe5jDwADR0IIvY1FWWo64FTCH7ctqkCxuMPMhyRE+YWlDZPQyIeSQqF3YAUAovZG/IBM2Pld1J44LY+Qz0zI5PnCD6632iXZ7X6Wv+SewKBgQCqtd1P1qJiVvrLnak+Eh23IRxm0xFkoOewYsYX7hhdFEh+ADebK7h8+IG8bRTP7Aq4omWbvWxSqV2E3R5a6AbQWbA7+oVp4UFDTDSeHplh9+o5hJG+lJ4bhQ+VNV3kFJxxE6v7CHMKhFU/QvajOEjXou4T0MxSEjVyiWFlandB7QKBgEj3kNyDmi258pi1tsLH0O1W6ydILdWvJpKaCeGzhUYyEyaI7ilP7Jplgfvv8ICcM32S7TNk370cwA1i+mjddDlItLJZvCONm3lJ37v56XF4IFEmVxj24B0B38pHMmqnHHwrZsImd1G7cM3UDk5t8espiJ2TYvXROBpr9A1JTSc1AoGACN3hXOA9FvaFmEXcIIk4WeayXvTflmZ1Ikg/GccYJN2E1maUfM7MilEuC+duiU4tUGG+/VPdaH27icciaH5cvD2sVbExdMpqZWBz6zKTrO7/j3U/TLLTc4J5yLo44jvf1E+9QxCxSP6OnvaPcPLfdbPUwdq6ghjIBeb01BuvjhECgYEAj+pZmwZ26iEJBV2KpWIfvTNVoXyM+6JyFTNYf55Kmr3qQUoYvLp+Ie4zDKOXknXyVa5taSs25JKnJOvfu/6fBi7k4XQYo0mXRzLPiZ/e5W/jAXi9MkI5coyDVMTHWVJ2+2QeccKzT6GqGAlFrnqPKxdRHrnC1xL2sSMQDzcBFX0=";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://120.25.125.138:8081/alipay/notifyUrl.go";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://120.25.125.138:8081/alipay/returnUrl.go";
    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4Ms8p71L+tKwacnt5k8Ymo1DwyLv+4wg3vzk6zgcdahWW6SQb89FayOw+iOyhNf/ST9yzbd2hzgGf3FiC+wjFlzAaLpbQJ9HKrYJ7HkfYDubocVRDTo7XKl4hkbihNHZUBPJBqvdgonUpPSCrJFyDqLlkbxyOY7MwM5IQu4y74AEJnSiHO3qI0oYPYPiQscHTZ1k1F9byTsgK57mSwwIYDeM7QstCceqovj5vKB2A1cB+heUSUo96hOUK7c/sebcoMvXZRhLfzaDrAlNwkGrm4SS2Jeyw0Wwunn8ujP5XbjJJClP5srnHx27I3xQVT+HQsuCfK6Kghbrp2DbEIJtPQIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
