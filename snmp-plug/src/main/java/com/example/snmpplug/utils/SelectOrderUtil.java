package com.example.snmpplug.utils;

import com.example.snmpplug.dto.SelectDatabase;
import com.example.snmpplug.mapper.SnmpWorkerMapper;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.*;

/**
 * @ClassName SelectOrderUtil
 * @Description: 查询数据入库
 * @Author wuyufei
 * @Date 2020/9/23
 * @Version V1.0
 **/
@Component
public class SelectOrderUtil {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    SnmpWorkerMapper snmpWorkerMapper;
    @Autowired
    RegUtil regUtil;

    /**
     * 查询snmp_worker结构树
     */
    public List<Map<String, Object>> snmpCollectOfParamFirm() {
        //查询所有厂商
        List firmOfType = new ArrayList();
        List<String> firmList = snmpWorkerMapper.selectAllFirm();
        if (!firmList.isEmpty()) {
            for (String firm : firmList) {
                Map<String, Object> typeMap = new HashedMap();
                //根据厂商查询设备类型
                List<String> typeList = snmpWorkerMapper.selectAllType(firm);
                typeMap.put("type", typeList);
                typeMap.put("firm", firm);
                firmOfType.add(typeMap);
            }
            return firmOfType;
        }
        return null;
    }

    /**
     * 查询入库
     */
    public boolean selectSnmpAllParam(SelectDatabase selectDatabase) {
        BufferedReader br = null;
        String ip = selectDatabase.getIp();
        String secret = selectDatabase.getPassword();
        //待定
        String index = selectDatabase.getIndex();
        //根据厂商，类型，指标名查询oid
        String firm = selectDatabase.getFirm();
        String type = selectDatabase.getType();
        String alias = selectDatabase.getAlias();
        List<Map<String, String>> oidList = snmpWorkerMapper.selectOid(firm, type, alias);
        //循环发snmp指令并入库
        Map<String, String> indexMap = new HashMap<>();
        for (Map<String, String> oidAndVersion : oidList) {
            //库中查询oid和version,rule
            String oid = oidAndVersion.get("oid");
            String version = oidAndVersion.get("version");
            String rule = oidAndVersion.get("rule");
            //发送snmp指令
            br = regUtil.snmpCa(secret, ip, oid, version, index);
            //String line = null;
            String line = "SNMPv2-SMI::mib-2.47.1.1.1.1.5.16847367 = INTEGER: 10";
            int k = 0;
            /* while ((line = br.readLine()) != null) {*/
            while (k < 3) {
                logger.info("进入while循环");
                //截取每一行数组并转换成list
                List lineStr = Arrays.asList(regUtil.regx(line));
                logger.info("每一行截取后的数据" + lineStr);
                //根据规则rule获取入库参数
                String[] str = rule.split(",");
                if ((str.length) != 0) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 1; i < str.length; i++) {
                        //将rule的每一个下标值作为每一行截取过的list的下标取出值
                        String value = (String) lineStr.get(Integer.parseInt(str[i]));
                        builder.append(value).append(" ");
                    }
                    //判断map中索引是否重复，重复追加，否则新增
                    if (indexMap.get(str[0]) != null) {
                        //追加
                        indexMap.put((String) lineStr.get(Integer.parseInt(str[0])), indexMap.get(str[0]) + builder.toString());
                    } else {
                        //索引 值 放入map,如果值有多个，以空格隔开（"123"，"12312 13123 412412"）
                        indexMap.put((String) lineStr.get(Integer.parseInt(str[0])), builder.toString());
                    }
                }
                k++;
            }
        }
        return false;
    }

}
