package com.yxsd.kanshu.pay.config;

/**
 * Created by lenovo on 2017/8/5.
 */
public class AlipayConfig {
    // 商户appid
    public static String APPID = "2016082000295780";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCKcMt1CCvUQY28MjhkohILTUvQNNt3099RajQCveFSZL35rWMfua0N16WK8gnZPBvTnXnda0LxzUcgyH2Lmv0N9i/Z8yrh8v9ZOxWIpQV0SrdL5Kxoawd7UcVZppMyNp9Z/7T5DQ+qODeCew+yVWicY3bVWk1jse6zY6SYo+rwtL8gXxI4LGuPslGZJ/kAUDl8w9vDMHefuk1yOrGGwGPDI5Aj+uwsoyEFctp6yJliuihDlNbWqvu/Rbh5C1Bk67mvO60mVUe6BvZzOPxPqp/XHi9lSD5YVWnm+u/TiiLPSZQoryJBJdB+OfSeMAxAHiYgGF9JFUYobJ7/Dhmqu9ozAgMBAAECggEAWRczZ5IlmofBLY5oCiLeV9WbZ1bMsbVwblVZOTZ2zW41b3rsYls8Cb9wMhWGzvzGr40VAq/nOfKvxmIfoPrRxO1cZ/b77sJRH1xNhE0mUGtQwA9eACh4fRznUsVGzramH+XV0bxdC174IgkN1ebu1k2QCMb/LJwyD9Gc9/ns1I+TToA/QKNkyvBdb7fcjqo/wKI4sKPUWFOCr7tMsq9Merm7mtBaJe6ia6XpiBX1kDkiIr+BH/iVk45Qd5gzF4CCz+90XATX7JTFM9wpnGKMI5E5tbF1PMehtjth4dsP7W7gioG3S0NJXNgvt4IO4AhddZMWM0i+3QrMFVb7z3fzyQKBgQDvx/TJpkTv3EkjTVDH5KszFV4zpAxryrsT1Wx69qJ1z5HSVmyAYH1IsaURs85RWnKjoqTaXNavr8KzFqKHStTjvy3MGkBnovCGKwe6veIhk0ueTkS9FAiiM/zUPculqv+h2ki7HwSkaE6DbklTrp5z4yI9iciCL+1IZrbIkhChNQKBgQCTzgd3W5et0w1zFJP3KspSgnUDAHEid0WU69emOD+WG2YlImiLexjfFNzP+FwZxzrGa+meoCUBgKmPGZP7L9WLof46nld8vzJZer2e7AMQpaZPINcEy4bosj1G6ShbKMBfDyS+1o4i1x3fuQK1CPv4JvLRqgmEA1DtO2wDrv+ixwKBgHA+43luK0E5lsc+zzzz76yXKST1e5cOLSsosoKRaBXMWE/lgx9Ji/x2pHY3/LIarCWr4o4R/bH6jiM1ylmZDzWZ7j9oXpPlM8Eq7ZBeiTf8ct6ldwooEa6UtZtJXkasWMSUoY1vBMZNycs+2nag/K31OrciA8nl06cb5h7YOY6tAoGAIHp4d4jf5qlm3i/aZ8i2VrUmAzfYpGszFgtRAsx/ZHu20VgjQusUxwhtkSGRE3UM/EcAOgQUvaTJHZHQS7TyJtG87KKYtOfKp5DqE/7mmYTc66xas0oJ5h4siuV3IbjttLW+LFU3PH6qqNudtaw4ugnM97d2idS0tryE+rY8pEcCgYEAvIY3SAJ1HHlrA8IoZNg6nO5nhhNfD8j+EN7P/D5ETV3D6luzHC/1RQiUR8lcF4wUgibYcNwTk10lZ7TSu2rG9EexGFvCXUAODy+Twh+ayn4AJ3SDiHsAcaNaneUABhrhuZz7x7Jcat4VMFQwLytgXylsTzzDN0rPfn8BDpUsijk=";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://120.25.125.138:8083/alipay/notifyUrl.go";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://120.25.125.138:8083/alipay/returnUrl.go";
    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs0qCwJ9kcmIvKwIo7cJjTY4qHgz6axgDYXy5bam0LN9jF2jvTeqUhGBVVtaKx2ZTD684h5qHIZY+vx01jESfnKdKQ2HTsoaC3yIWRK3qORkxDxCqX2wRQLYeU5/Mm5B7TGQTGvgtZeB/H/KqHcNCoIG+vcPnNmylw0rSLBhvL+bLVSSI0jlxDjFT8PjT3jjAgLzBNKHFRpgkDOYQm4B45cC1LlW30x119IyrItYhr3vy8iNzK8oSJu7E/qF0rfriwV40hx2IJwkToGbMAEeUTbngoDQ5Nbzh1zwOtt8eJ1pKr3wYb4aYQ2meFi5kKSM0ZUNMUmp20DfpUqjdnc4tIwIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
