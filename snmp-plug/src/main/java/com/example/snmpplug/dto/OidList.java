package com.example.snmpplug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName OidList
 * @Description: dto
 * @Author wuyufei
 * @Date 2020/9/23
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OidList {
    //下标组合（规则）
    private String rule;
    //索引
    private String index;
    //oid
    private String oid;
}
