package com.example.snmpplug.telnet;

import org.springframework.stereotype.Service;

@Service
public class TelentDemo {
    public String telnetColl(String host, String username, String password) {
        TelnetBean telnetBean = new TelnetBean();
        return telnetBean.collectRountCsco(host, username, password);
    }
}
