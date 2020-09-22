package com.example.snmpplug.repository;

import com.example.snmpplug.model.SnmpInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author jinxin
 * @Date 2020/9/22 9:50 上午
 */
@Repository
@Mapper
public interface SnmpInfoRepository {
    /**
     *
     * @param catagory
     * @param type
     * @param performance
     * @return
     */
    @Results(
            id = "snmpInfo",
            value = {
                   @Result(property = "type",column = "type"),
                   @Result(property = "performance" ,column = "performance"),
                   @Result(property = "catagory",column = "catagory"),
                   @Result(property = "protocol",column = "protocol"),
                   @Result(property = "oid",column = "oid"),
                   @Result(property = "ruleData",column = "rule_data"),
                   @Result(property = "needIndex",column = "need_index"),
                   @Result(property = "midRegx",column = "mid_regx"),
                   @Result(property = "indexRegx",column = "index_regx"),
                   @Result(property = "indexNum",column = "index_num"),
                   @Result(property = "valueRegx",column = "value_regx"),
                   @Result(property = "valueNum",column = "value_num"),
            }
    )
    @Select(
            "select * from snmp_info where performace =#{performance} and type =#{type} and catagory=#{catagory}")
    List<SnmpInfo> selectSnmpInfosByConditions(String catagory, String type, String performance);
}
