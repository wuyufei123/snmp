package com.example.snmpplug.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author jinxin
 * @Date 2020/9/22 9:53 上午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnmpInfo {
    private String type;
    private String performance;
    private String catagory;
    private String protocol;
    private String oid;
    private String ruleData;
    private String needIndex;
    private String midRegx;
    private String indexRegx;
    private String indexNum;
    private String valueRegx;
    private String valueNum;

}
