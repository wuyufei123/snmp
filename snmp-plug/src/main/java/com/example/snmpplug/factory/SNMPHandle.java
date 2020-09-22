package com.example.snmpplug.factory;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * @Author jinxin
 * @Date 2020/9/15 10:52 上午
 */
public interface SNMPHandle {
    JSONObject handle(BufferedReader bufferedReader, JSONObject regxObject);
    ArrayList index(BufferedReader bufferedReader, JSONObject regxObject);
}
