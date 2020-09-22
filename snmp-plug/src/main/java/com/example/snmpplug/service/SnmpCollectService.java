package com.example.snmpplug.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author jinxin
 * @Date 2020/9/22 9:36 上午
 * snmp采集service
 */
public interface SnmpCollectService {
    void snmpDataCollect(JSONObject jsonObject);
}
