package com.example.snmpplug.mapper;

import com.example.snmpplug.model.SnmpWorker;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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
    //查询返所有厂商
    List<String> selectAllFirm();
    //根据厂商查询设备类型
    List<String> selectAllType(String firm);
    //根据厂商，类型，指标名查询oid
    List<Map<String,String>> selectOid(String firm, String type, String alias);
}
