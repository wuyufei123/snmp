package com.example.snmpplug.factory;

import com.alibaba.fastjson.JSONObject;
import com.example.snmpplug.utils.SplitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * @Author jinxin
 * @Date 2020/9/15 2:11 下午
 * 截取采集数据
 */
@Component
public class DataHandle implements SNMPHandle {
    private static final Logger logger = LoggerFactory.getLogger(DataHandle.class);
    JSONObject data = new JSONObject();
    ArrayList arrayList = new ArrayList();
    @Autowired
    SplitUtil splitUtil;

    @Override
    public JSONObject handle(BufferedReader bufferedReader, JSONObject regxObject) {
        // String valueRegx, int valueNum, String indexRegx, int indexNum
        String midRegx = (String) regxObject.get("MIDREGX");
        String valueRegx = (String) regxObject.get("VALUEREGX");
        int valueNum = Integer.valueOf((String) regxObject.get("VALUENUM"));
        String indexRegx = (String) regxObject.get("INDEXREGX");
        int indexNum = Integer.valueOf((String) regxObject.get("INDEXNUM"));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (!midRegx.isEmpty()) {
                    String[] split = splitUtil.spiltString(line, midRegx);
                    if (valueRegx != null && indexRegx != null) {
                        //split[1].split(valueRegx)
                        String value = splitUtil.spiltString(split[1], valueRegx)[valueNum].trim();
                        //split[0].split("\\.")
                        String[] strings = splitUtil.spiltString(split[0], indexRegx);
                        String index = strings[strings.length - indexNum];
                        for (int i = indexNum; i > 1; i--) {
                            index += strings[strings.length - i];
                            if (i != 1) {
                                index += ".";
                            }
                        }
                        data.put(index, value);
                    }
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    /***
     * @<Description>: 获取索引
     * @Param [bufferedReader, regxObject]
     * @return: java.util.ArrayList
     * @Author: jinxin
     * @Date: 2020/9/24 9:37
     * @version 1.0.0
     */
    @Override
    public ArrayList index(BufferedReader bufferedReader, JSONObject regxObject) {
        String midRegx = (String) regxObject.get("MIDREGX");
        String valueRegx = (String) regxObject.get("VALUEREGX");
        //  int valueNum = Integer.valueOf((String) regxObject.get("VALUENUM"));
        String indexRegx = (String) regxObject.get("INDEXREGX");
        int indexNum = Integer.valueOf((String) regxObject.get("INDEXNUM"));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (!midRegx.isEmpty()) {
                    String[] split = splitUtil.spiltString(line, midRegx);
                    if (valueRegx != null && indexRegx != null) {
                        //split[1].split(valueRegx)
                        // String value = splitUtil.spiltString(split[1], valueRegx)[valueNum].trim();
                        //split[0].split("\\.")
                        String[] strings = splitUtil.spiltString(split[0], indexRegx);
                        String index = strings[strings.length - indexNum];
                        arrayList.add(index);
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}

