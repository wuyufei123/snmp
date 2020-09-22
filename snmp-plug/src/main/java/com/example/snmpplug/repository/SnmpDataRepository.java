package com.example.snmpplug.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author jinxin
 * @Date 2020/9/22 9:51 上午
 */
@Repository
@Mapper
public interface SnmpDataRepository {
    @Insert(
            "insert into snmp_data" +"(ip,performance,value)"+"values"+"(#{ip},#{performance},#{value})"
    )
    int saveSnmpDataById(String ip,String performance,@Param("value") String value);
}
