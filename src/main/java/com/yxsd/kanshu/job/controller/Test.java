package com.yxsd.kanshu.job.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by lenovo on 2017/8/29.
 */
public class Test {
    public static void main(String[] args) {
       String jsonStr = "[{'bookId':123,'index':50},{'bookId':555,'index':100}]";
       String s = "%5B%7B%27bookId%27%3A123%2C%27index%27%3A50%7D%2C%7B%27bookId%27%3A555%2C%27index%27%3A100%7D%5D %E7%BE%8E%E5%A5%B3";
        try {
            System.out.println(URLEncoder.encode((jsonStr),"UTF-8"));
            System.out.println(URLEncoder.encode("美女","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = JSONObject.parseArray(jsonStr);//media对象参数
        for(int i = 0 , len = jsonArray.size(); i< len ;i++) {
            Map map = (Map) jsonArray.get(i);
            int bookId = Integer.parseInt(map.get("bookId").toString());
            System.out.println(bookId);
        }

        System.out.println(String .format("%.1f",243435 / 10000.0));

    }

}
