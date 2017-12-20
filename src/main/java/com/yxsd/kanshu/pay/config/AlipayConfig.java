package com.yxsd.kanshu.pay.config;

/**
 * Created by lenovo on 2017/8/5.
 */
public class AlipayConfig {
    // 商户appid
    public static String APPID = "2017111609966996";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCSrd21ymWc9m6OZIpPUcJ03PbRJhkGfiLlC6Abze/lomp1NMvjfKCmi6smC9NzO8KWi3XEJGQzNqJP+C/JtkhhSaQuiD9Spp+MD0zJmycPZfewz2Ek7O+pE7j1uUAyv8iPDFWiUeALAyRPgiJv8+bIqsDmFVGhgAcUYThWsR5kSwpQUpaHrtYnQYLlHFMhDU8UlpP6DVoAaTzUAO+QnRsSLFJqEGWEF3j87VCu4bbI2J6b98pARMGE7+Pyhq/FMzMsKj/tVY1q32RoeOE7xTUHvAX3pjExBbUeyl2JS/6pzfsahCztgHJgeMDpy6uackpDFffH8wTsrG6Qb6xwwfFPAgMBAAECggEAMQsze6peCXqYgsH5QQZMZ7jOSiZb7cDVOBKICWP1V6nTwLbTiY/NQ4KlykXgAMK1kJK24MtuOXWGFgkdKWvqjaUK4Ygq7B7McA9ZlHttNwfYJuVgTPFwPO9cQ8UaQx2/GJ1+MJUBvYziYjdX/mjl/KijcQslzUM6msTbCpmUWOIAzUd9KLSVgVlv6xSUW6BvKJE7XFMFxkm7RnPECpRIwgTyU8AHaf7Vm3MClNL5il/mZkX6ICYVfWC7ZUoukeh48R4YSW0aSGyyZ7skYhedSsRyHN6l7N132ZINEk8yJGgGmpGNHJjD9/a1UDUt4YHFdKUmUn/F+2YQ7dIZn2PxgQKBgQDDB2az7ZA19z5P9W5LEj3v3UdFTX6DAhKHqb5nk6bA4/Erb6FKzZOsZkvV2CuwxNNUuyb4NyhpNby3YzpI7vrEOz+z5NFLMNvkKIPfzLOJwy4AFngRFzEyhl7nUPT42VHeDZcKQj9Xdt2KC6R5iKIQDi+1d07cfb18DD3sEG1QjwKBgQDAiO1CMrthQR3PCFj6MDCG0eaM9X/ZlVpwhCUh0fF9BrBk+C/RZ5KnAJWcKMlFqIGJCDdydYW/z72UfNJSEGwnYOSkgIzoPcmRstZEdzY4IbVw+04gVxm+jMMA4E8GzOV0fM7zkCn0ktIAl3HJp42INMyF0iopumP3vWRO9MwzQQKBgCPHXNEPeRBdbmIO3ZAKJbC2gJe3Msln/OxEeLjZvpLOfnUAgq+NbVUIY0s04DcKtNZriyeoPtKmWBuwqYWtyfVsBTDEfpzztrNxt1e8Xweb9flwJdDE13K0cf6vvHFWtQ0uoxHvu10ZYmUr452kMk8NuzToUWBj9Pon6MhA7z0DAoGAU9PKKUZZWzC0v81MoDGXDzwYe0rwTVW/uLlwSVdVIzBkYLHGcA/+RAP3H7SKXW/OZlFTgtw0TZ0vV5U5JU3NKSAArLbjosI0HuGUdN7Klonmq7he/amupc43oQwInFddl+g3Wshr2/aIxIUrn7b54lHKsoEcpmpm2Bh3sgDXX4ECgYAygsvZAef+Uo0ae9ja0hmlWxtecEDS6+QKBGASDxFSN0PJb30mIEnBY8j/4/ITYYpTyw35pXJlaT+5LGdj+vnwXc7U2KcOJrkYs8gEaU2xknISTd9VQek5/PdoAWSIUB1prSMgzveiKNVIbOapGSjnFoo0DLc12D6zA6Iq8aMYnQ==";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://read.chunnuan999.com/alipay/notifyUrl.go";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://read.chunnuan999.com/alipay/returnUrl.go";
    // 请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnBWP5RV+tL6yEZfsPngWXuCdvM34n8X799iJXx6luLrGMuvyEiBnRkfdvv03n109w82xUVXXKHwwcJFH2NR2bo9QtcsMcut/BMzpsffiXM3lW4WD2OF/u5jluOyrgYqAjHMsxPcYLk4DjeLavk7rxJvsCXHD+/vjSIdlHJtThlyzQvD3+3Z+ITyn9LJ6VoBUni60b88lP7+3NRJJ7pTv7fw59r2fIbudE8E3fm7RGBQ795WkkRsPkSeRHstWr1WTUvdb0C6uEeVYl/Ah2QuNAbhADBPoH3MWXn94jPE33BLyOGNlCFkPXg7AjLsoyYMS3CdyP9S3DpBLAGu9lvILIQIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
