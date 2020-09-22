package com.example.snmpplug.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.snmpplug.factory.SNMPCollect;
import com.example.snmpplug.factory.SNMPFormat;
import com.example.snmpplug.factory.SNMPHandle;
import com.example.snmpplug.model.SnmpInfo;
import com.example.snmpplug.repository.SnmpDataRepository;
import com.example.snmpplug.repository.SnmpInfoRepository;
import com.example.snmpplug.service.SnmpCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author jinxin
 * @Date 2020/9/22 9:38 上午
 */
@Service
public class SnmpCollectServiceImpl implements SnmpCollectService {
    @Autowired
    SnmpDataRepository snmpDataRepository;
    @Autowired
    SnmpInfoRepository snmpInfoRepository;
    @Autowired
    SNMPCollect snmpCollect;
    @Autowired
    SNMPHandle snmpHandle;
    @Autowired
    SNMPFormat snmpFormat;

    @Override
    public void snmpDataCollect(JSONObject jsonObject) {
        //采集数据
        JSONObject collectObject=new JSONObject();
        //分割参数
        JSONObject regxObject=new JSONObject();
        ArrayList arrayList=new ArrayList();
        boolean flag=true;
        String ip = (String) jsonObject.get("IP");
        String performance = (String) jsonObject.get("PERFORMANCE");
        String community = (String) jsonObject.get("COMMUNITY");
        String type = (String) jsonObject.get("TYPE");
        String catagory = (String) jsonObject.get("CATAGORY");
        collectObject.put("IP",ip);
        collectObject.put("COMMUNITY",community);
        List<SnmpInfo> snmpInfoList = snmpInfoRepository.selectSnmpInfosByConditions(catagory, type, performance);
        JSONObject[] formatArray=new JSONObject[snmpInfoList.size()];
        for (SnmpInfo snmpInfo : snmpInfoList) {
            String protocol = snmpInfo.getProtocol();
            String oid = snmpInfo.getOid();
            collectObject.put("PROTOCOL",protocol);
            collectObject.put("OID",oid);
            BufferedReader bufferedReader = snmpCollect.collect(collectObject);
            String midRegx = snmpInfo.getMidRegx();
            String indexRegx = snmpInfo.getIndexRegx();
            String indexNum = snmpInfo.getIndexNum();
            String valueRegx = snmpInfo.getValueRegx();
            String valueNum = snmpInfo.getValueNum();
            regxObject.put("MIDREGX",midRegx);
            regxObject.put("INDEXREGX",indexRegx);
            regxObject.put("INDEXNUM",indexNum);
            regxObject.put("VALUEREGX",valueRegx);
            regxObject.put("VALUENUM",valueNum);
            //查询索引
            if(flag){
                 arrayList = snmpHandle.index(bufferedReader, regxObject);
                 flag=false;
            }
            //数据处理
            JSONObject handleObject = snmpHandle.handle(bufferedReader, regxObject);
            //存入数组
            formatArray[snmpInfoList.indexOf(snmpInfo)]=handleObject;
        }
        //格式化数据
        String formatData = snmpFormat.format(arrayList, formatArray);
        //数据入库
        snmpDataRepository.saveSnmpDataById(ip,performance,formatData);
    }
}
