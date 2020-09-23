package com.example.snmpplug.service;

import com.example.snmpplug.dto.DadaSource;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CreateOrderService
 * @Description: 新增规则
 * @Author wuyufei
 * @Date 2020/9/23
 * @Version V1.0
 **/
public interface CreateOrderService {
    Map<String, List<String[]>> insertOfCollect(DadaSource listDadaSource);

}
