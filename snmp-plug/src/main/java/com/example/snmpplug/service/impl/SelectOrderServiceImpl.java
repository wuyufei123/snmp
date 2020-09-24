package com.example.snmpplug.service.impl;

import com.example.snmpplug.service.SelectOrderService;
import com.example.snmpplug.utils.SelectOrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SelectOrderServiceImpl
 * @Description: TODO
 * @Author wuyufei
 * @Date 2020/9/24
 * @Version V1.0
 **/
@Service
public class SelectOrderServiceImpl implements SelectOrderService {
    @Autowired
    SelectOrderUtil selectOrderUtil;
    @Override
    public List<Map<String, Object>> snmpCollectOfParamFirm() {
        return selectOrderUtil.snmpCollectOfParamFirm();
    }
}
