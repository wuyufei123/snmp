package com.example.snmpplug.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author jinxin
 * @Date 2020/9/15 2:59 下午
 */
@Component
public class SplitUtil {
    public String[] spiltString(String srcString,String c){
   // 返回分割结果数组存入list中返回
        if(srcString==null || c==null){
            return null;
        }else {
            // 特殊字符需转义
            if ("\\".equals(c)) {
                c = "\\\\";
            } else {
                String rex = "[*+?|.$^]";
                Pattern pattern = Pattern.compile(rex);
                Matcher mat = pattern.matcher(c);
                if (mat.matches()) {
                    // 特殊字符需转义
                    c = "\\" + c;
                }
            }
            String[] temp = srcString.split(c);
            return temp;
        }
    }
}
