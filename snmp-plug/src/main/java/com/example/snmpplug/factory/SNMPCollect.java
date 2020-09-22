package com.example.snmpplug.factory;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;

/**
 * @Author jinxin
 * @Date 2020/9/15 9:46 上午
 */
public interface SNMPCollect {
    BufferedReader collect(JSONObject jsonObject);
    //JSONObject handle(String data,String valueRegx,int valueNum,String indexRegx ,int indexNum);
}
