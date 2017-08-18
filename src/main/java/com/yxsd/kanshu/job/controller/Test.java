package com.yxsd.kanshu.job.controller;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/8/17.
 */
public class Test {
    public static void main(String[] args) {
        String[] cbids = {"1","","re","fsa","43"};
        List<String> tempList = new ArrayList<String>();
        for (String cbid : cbids) {
            if(StringUtils.isNotBlank(cbid)){
                tempList.add(cbid);
            }
        }
        cbids = tempList.toArray(new String[0]);
        System.out.println(11);
    }
}
