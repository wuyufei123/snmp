package com.example.snmpplug.factory;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 格式化采集数据
 *
 * @Author jinxin
 * @Date 2020/9/21 3:52 下午
 */
@Component
public class DataFormat implements SNMPFormat {
    private static final Logger logger = LoggerFactory.getLogger(DataFormat.class);
    StringBuffer sb = new StringBuffer();

    @Override
    public String format(ArrayList<String> arrayList, JSONObject[] params) {
        try {
            for (String index : arrayList) {
                sb.append(index).append(",");
                for (JSONObject jsonObject : params) {
                    sb.append(jsonObject.get(index)).append(",");
                }
            }
            String data = sb.toString();
            return data;
        } catch (Exception e) {
            logger.error("DataFormat异常");
        }
        return null;
    }
}
