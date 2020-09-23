package com.example.snmpplug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DadaSource
 * @Description: dto
 * @Author wuyufei
 * @Date 2020/9/15
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadaSource {
    private String ip;
    private String password;
    //snmpwalk版本
    private String version;
    //厂商
    private String firm;
    private String type;
    private String alias;
    private List<OidList> oidList;
}
