package com.example.snmpplug.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName snmpworker
 * @Description: TODO
 * @Author wuyufei
 * @Date 2020/9/22
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnmpWorker {
    private String type;
    //指标名
    private String alias;
    //厂商
    private String firm;
    private String oid;
    //规则
    private String rule;
    //snmpwalk版本
    private String version;
}
