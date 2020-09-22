package com.example.snmpplug.template;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;

/**
 * @Author jinxin
 * @Date 2020/9/21 3:30 下午
 */
public abstract class SNMP {
    abstract BufferedReader dataCollect();
    abstract JSONObject dataHandle();
    abstract void dataFormat();

    //采集模版
    public final void play(JSONObject jsonObject){

    }
}
