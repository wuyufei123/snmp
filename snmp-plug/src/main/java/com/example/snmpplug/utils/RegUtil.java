package com.example.snmpplug.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @ClassName regUtil
 * @Description: TODO
 * @Author wuyufei
 * @Date 2020/9/24
 * @Version V1.0
 **/
@Component
public class RegUtil {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 正则匹配过滤符号并截取（snmp指令每一行）
     *
     * @param line
     * @return
     */
    public String[] regx(String line) {
        //模拟需要过滤的符号  (?::|=|\.)  匹配一个和多个空格截取
        return (line.replaceAll("(?::|=|\\.|\\(|\\))", " ").trim()).split("(?:' '|\\s+)");
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
