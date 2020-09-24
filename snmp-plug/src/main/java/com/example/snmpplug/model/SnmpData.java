package com.example.snmpplug.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**数据实体类
 * @Author jinxin
 * @Date 2020/9/22 9:55 上午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnmpData {
    //设备ip
    private String ip;
    //设备性能指标
    private String performance;
    //private String index;
    //采集数据
    private String value;
}
