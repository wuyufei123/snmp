package com.example.snmpplug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SelectDatabase
 * @Description: 查询入库
 * @Author wuyufei
 * @Date 2020/9/23
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectDatabase {
    private String ip;
    private String password;
    //厂商
    private String firm;
    //类型
    private String type;
    //性能指标
    private String alias;
    //索引（待定）
    private String index;
}
