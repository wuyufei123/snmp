package com.example.snmpplug;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages="com.example.snmpplug",annotationClass= Mapper.class)
public class SnmpPlugApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnmpPlugApplication.class, args);
    }
}
