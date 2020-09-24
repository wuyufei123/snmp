package com.example.snmpplug.controller;

import com.example.snmpplug.dto.SelectDatabase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SelectOrderController
 * @Description: 选择规则查询并入库数据
 * @Author wuyufei
 * @Date 2020/9/23
 * @Version V1.0
 **/
@RestController
@RequestMapping("select")
public class SelectOrderController {
    @PostMapping("SnmpCollect")
    public String SnmpCollect(@RequestBody SelectDatabase selectDatabase) {
        
        return null;
    }


}
