package com.example.snmpplug.utils;

import com.example.snmpplug.dto.DadaSource;
import com.example.snmpplug.dto.OidList;
import com.example.snmpplug.mapper.SnmpWorkerMapper;
import com.example.snmpplug.model.SnmpWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName CreateOrderUtil
 * @Description: TODO
 * @Author wuyufei
 * @Date 2020/9/15
 * @Version V1.0
 **/
@Component
public class CreateOrderUtil {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    SnmpWorkerMapper snmpWorkerMapper;

    /**
     * 正则匹配过滤符号并截取（snmp指令每一行）
     *
     * @param line
     * @return
     */
    public String[] regx(String line) {
        //模拟需要过滤的符号  (?::|=|\.)  匹配一个和多个空格截取
        return (line.replaceAll("(?::|=|\\.)", " ").trim()).split("(?:' '|\\s+)");
    }

    /**
     * 输入信息等查询snmp指令
     *
     * @param
     * @return listDadaSource show
     */

    /**
     * 入参格式
     * {
     * "ip": "1",
     * "password": "1",
     * "version": "0",
     * "firm": "0",
     * "type": "0",
     * "oidList": [
     * {
     * "rule": [
     * "1",
     * "2"
     * ],
     * "index": "42312523",
     * "oid": "123123123"
     * },
     * {
     * "rule": [
     * "1",
     * "2"
     * ],
     * "index": "42312523",
     * "oid": "uajscasc"
     * }
     * ],
     * "alias": "123"
     * }
     */

    public Map<String, List<String[]>> collect(DadaSource dadaSource) throws IOException {
        BufferedReader br = null;
        //解析参数
        //版本
        String version = dadaSource.getVersion();
        //ip
        String ip = dadaSource.getIp();
        //密码
        String community = dadaSource.getPassword();
        //厂商
        String firm = dadaSource.getFirm();
        //型号
        String type = dadaSource.getType();
        //指标名
        String alias = dadaSource.getAlias();
        //oidList（包含索引，规则，oid）
        List<OidList> oidList = dadaSource.getOidList();
        //待完善（此处不考虑索引拼接的情况）
        //循环发送指令
        /**
         *  "oidList": [
         *               {
         *                   "rule": "1，2，3",
         *                   "index": "42312523",
         *                   "oid": "123123123"
         *              }]
         */
        Map<String, List<String[]>> map = new HashMap<>();
        for (OidList listAll : oidList) {
            //规则
            String rule = listAll.getRule();
            //索引
            String index = listAll.getIndex();
            //oid
            String oid = listAll.getOid();
            // String line = null;
            String line = "SNMPv2-SMI::mib-2.47.1.1.1.1.5.16847367 = INTEGER: 10";
            //判断是展示还是新增
            //展示10行
            List<String[]> list = new ArrayList<>();
            int i = 0;
            if (rule.split(",")[0].trim().isEmpty()) {
                br = snmpCa(community, ip, oid, version, index);
                /*while ((line = br.readLine()) != null) {*/
                while (true) {
                    if (i < 10) {
                        //处理过滤指定字符
                        list.add(regx(line));
                        //递增
                        i++;
                    } else {
                        map.put(oid, list);
                        break;
                    }
                }
            } else {
                //新增入库
                //待完善（考虑不拼索引，并且每列都作为插入数据库的唯一一列处理）
                //不用于展示，用户进行查询，数据入库操作，规则（索引，值）对应下标（2，4）
                //在snmp_worker中新增一列或多列
                snmpWorkerMapper.insertReg(new SnmpWorker(type, alias, firm, oid, rule, version));
            }
        }
        return map;
    }

    /**
     * 发送命令，返回参数
     */
    public BufferedReader snmpCa(String secret, String ip, String oid, String version, String index) {
        try {
            Process ps = Runtime.getRuntime().exec("snmpwalk -v" + version + " -c " + secret + " " + ip +
                    oid + " " + index);
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream(),
                    Charset.forName("utf8")));
            return br;
        } catch (IOException e) {
            logger.info("采集指令发送失败");
        }
        return null;
    }

}
