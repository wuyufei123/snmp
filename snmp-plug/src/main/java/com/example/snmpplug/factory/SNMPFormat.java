package com.example.snmpplug.factory;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * @Author jinxin
 * @Date 2020/9/21 3:52 下午
 */
public interface SNMPFormat {
    String format(ArrayList<String> list,JSONObject... params);
}
