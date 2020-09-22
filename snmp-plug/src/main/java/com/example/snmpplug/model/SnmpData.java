package com.example.snmpplug.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author jinxin
 * @Date 2020/9/22 9:55 上午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnmpData {
    private String ip;
    private String performance;
    private String index;
    private String value;
}
