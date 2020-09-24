package com.example.snmpplug.controller;

import com.example.snmpplug.dto.DadaSource;
import com.example.snmpplug.service.CreateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName createordercontroller
 * @Description: 新增采集规则
 * @Author wuyufei
 * @Date 2020/9/15
 * @Version V1.0
 **/
@RestController
@RequestMapping("create")
public class CreateOrderController {
    @Autowired
    CreateOrderService createOrderService;

    /**
     * 规则新增入库，规则新增snmp查询回显
     *
     * @param listDadaSource
     * @return
     */
    @PostMapping("SnmpCollect")
    public Map<String, List<String[]>> snmpCollect(@RequestBody DadaSource listDadaSource) {
        return createOrderService.insertOfCollect(listDadaSource);
    }
}
