package com.example.snmpplug.factory;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author jinxin
 * @Date 2020/9/21 3:52 下午
 */
@Component
public class DataFormat implements SNMPFormat {
    StringBuffer sb = new StringBuffer();
    @Override
    public String format(ArrayList<String> arrayList,JSONObject[] params) {
        for (String index : arrayList) {
            sb.append(index).append(",");
            for (JSONObject jsonObject : params) {
                sb.append(jsonObject.get(index)).append(",");
            }
        }
        String data= sb.toString();
        return data;
    }
}
