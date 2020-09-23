package com.example.snmpplug.mapper;

import com.example.snmpplug.model.SnmpWorker;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName SnmpWorker
 * @Description: TODO
 * @Author wuyufei
 * @Date 2020/9/23
 * @Version V1.0
 **/
@Mapper
public interface SnmpWorkerMapper {
    //新增规则
    Boolean insertReg(SnmpWorker snmpWorker);
}
