package com.example.snmpplug.controller;

import com.example.snmpplug.dto.DadaSource;
import com.example.snmpplug.service.CreateOrderService;
import com.example.snmpplug.utils.CreateOrderUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName createordercontroller
 * @Description: 新增采集规则
 * @Author wuyufei
 * @Date 2020/9/15
 * @Version V1.0
 **/
@Controller
@RequestMapping("create")
public class CreateOrderController {
    @Autowired
    CreateOrderService createOrderService;

    @PostMapping("SnmpCollect")
    public Map<String, List<String[]>> tes(@RequestBody DadaSource listDadaSource) {
        return createOrderService.insertOfCollect(listDadaSource);
    }

}
