package com.example.snmpplug.controller;

import com.example.snmpplug.dto.SelectDatabase;
import com.example.snmpplug.service.SelectOrderService;
import com.example.snmpplug.utils.SelectOrderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    SelectOrderUtil selectOrderUtil;
    @Autowired
    SelectOrderService selectOrderService;

    /**
     * 查询snmp_worker结构树firm，type
     */
    @RequestMapping("selectTree")
    public List<Map<String, Object>> snmpCollectFirm(){
        return selectOrderService.snmpCollectOfParamFirm();
    }
    /**
     * 根据参数查询snmp指令并入库
     */
    @PostMapping("selectSnmp")
    public boolean selectSnmpAll(@RequestBody SelectDatabase selectDatabase){
        return  selectOrderUtil.selectSnmpAllParam(selectDatabase);
    }

}
