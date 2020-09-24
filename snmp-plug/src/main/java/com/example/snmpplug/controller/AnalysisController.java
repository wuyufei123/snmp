package com.example.snmpplug.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.snmpplug.service.SnmpCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author jinxin
 * @Date 2020/9/15 8:58 上午
 */
@RestController
@RequestMapping("/anal")
public class AnalysisController {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisController.class);

    @Autowired
    SnmpCollectService snmpCollectService;

    /**接收前端参数，采集SNMP性能指标
     * @param request
     * @param jsonObject IP 查询的IP地址
     *                   performance 性能指标
     *                   community 密码
     *                   type 设备型号
     *                   catagory 设备种类
     */
    @PostMapping("/collect")
    public void testAnalysis(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        snmpCollectService.snmpDataCollect(jsonObject);
    }

  /***
   * @<Description>: 测试用
   * @Param []
   * @return: java.lang.String
   * @Author: jinxin
   * @Date: 2020/9/24 9:30
   * @version 1.0.0
   */
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
