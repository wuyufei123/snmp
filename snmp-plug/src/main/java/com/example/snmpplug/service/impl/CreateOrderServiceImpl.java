package com.example.snmpplug.service.impl;

import com.example.snmpplug.dto.DadaSource;
import com.example.snmpplug.service.CreateOrderService;
import com.example.snmpplug.utils.CreateOrderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CreateOrderServiceImpl
 * @Description: TODO
 * @Author wuyufei
 * @Date 2020/9/23
 * @Version V1.0
 **/
@Service
public class CreateOrderServiceImpl implements CreateOrderService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    CreateOrderUtil createOrderUtil;

    @Override
    public Map<String, List<String[]>> insertOfCollect(DadaSource listDadaSource) {
        try {
            return createOrderUtil.collect(listDadaSource);
        } catch (IOException e) {
            logger.error("新增规则失败");
        }
        return null;
    }
}
