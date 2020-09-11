package com.example.snmpplug.controller;

import com.example.snmpplug.telnet.TelentDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class telnetController {
    @Autowired
    TelentDemo telnetBean;

    @RequestMapping("test")
    public String telnet(@RequestParam String host,
                         @RequestParam String username,
                         @RequestParam String password) {
        return telnetBean.telnetColl(host, username, password);
    }
}
