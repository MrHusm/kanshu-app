package com.yxsd.kanshu.base.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.net.URLEncoder;
import java.util.*;

/**
 * Created by lenovo on 2017/10/5.
 */
public class CommonUtil {

    public static boolean isNumeric(String str) {
        if (str.matches("\\d *")) {
            return true;
        } else {
            return false;
        }
    }

    //map转成xml
    public static String mapToXml(HashMap<String, Object> arr) {
        String xml = "<xml>";

        Iterator<Map.Entry<String, Object>> iter = arr.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            String key = entry.getKey();
            String val = entry.getValue()+"";
            if (isNumeric(val)) {
                xml += "<" + key + ">" + val + "</" + key + ">";

            } else
                xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
        }

        xml += "</xml>";
        return xml;
    }

    //xml转成map
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(String xml) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        return map;
    }


    public static String formatParamMap(Map<String, Object> parameters){
        String buff = "";
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(
                    parameters.entrySet());
            Collections.sort(infoIds,
                    new Comparator<Map.Entry<String, Object>>() {
                        public int compare(Map.Entry<String, Object> o1,
                                           Map.Entry<String, Object> o2) {
                            return (o1.getKey()).toString().compareTo(
                                    o2.getKey());
                        }
                    });

            for (int i = 0; i < infoIds.size(); i++) {
                Map.Entry<String, Object> item = infoIds.get(i);
                if (item.getKey() != "") {
                    buff += item.getKey() + "="
                            + URLEncoder.encode(item.getValue()+"", "utf-8") + "&";
                }
            }
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buff;
    }

    public static String createNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }
}