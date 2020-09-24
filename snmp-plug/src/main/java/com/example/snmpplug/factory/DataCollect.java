package com.example.snmpplug.factory;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @Author jinxin
 * @Date 2020/9/15 10:33 上午
 * 无index拼接采集
 */
@Component
public class DataCollect implements SNMPCollect {
    private static final Logger logger = LoggerFactory.getLogger(DataCollect.class);
    Process ps = null;
    BufferedReader br = null;

    @Override
    public BufferedReader collect(JSONObject jsonObject) {
        String ip = (String) jsonObject.get("IP");
        String protocol = (String) jsonObject.get("PROTOCOL");
        String community = (String) jsonObject.get("COMMUNITY");
        String oid = (String) jsonObject.get("OID");
        try {
            //发送SNMP指令
            ps = Runtime.getRuntime().exec(protocol + community + " " + ip + oid);
            //转换流
            br = new BufferedReader(new InputStreamReader(ps.getInputStream(), Charset.forName("UTF-8")));
            return br;
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
        return null;
    }
}
