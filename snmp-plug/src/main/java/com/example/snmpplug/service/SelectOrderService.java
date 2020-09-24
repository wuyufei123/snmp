package com.example.snmpplug.service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SelectOrderService
 * @Description: TODO
 * @Author wuyufei
 * @Date 2020/9/24
 * @Version V1.0
 **/
public interface SelectOrderService {
    //查询snmp_worker结构树firm，type
    List<Map<String, Object>> snmpCollectOfParamFirm();

}
