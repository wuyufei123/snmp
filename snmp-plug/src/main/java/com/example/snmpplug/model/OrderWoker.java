package com.example.snmpplug.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName OrderWoker
 * @Description: TODO
 * @Author wuyufei
 * @Date 2020/9/22
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWoker {
    //ip
    private String ip;
    //别名
    private String alias;
    //索引
    private String index;
    //值
    private String value;
}
