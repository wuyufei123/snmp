package com.example.snmpplug.telnet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: wuyufei
 * @Date: 2020/8/28 14:03
 * @Description: telnet跳板机
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelnetBean {
    Object lock = new Object();
    TelnetClient telnet = null;
    String hostname = "202.102.101.217";
    int hostport = 20002;
    String user = "jsict@default";
    String password = "jsict";
    private InputStream in;
    private PrintStream out;
    //编号
    private static final String ORIG_CODEC = "ISO8859-1";
    //编码格式
    private static final String TRANSLATE_CODEC = "GBK";
    //log
    private final Logger logger = LoggerFactory.getLogger(getClass());
    //华为设备一次展示全部
    private static final String srean = "screen-length 0 temporary\n";
    //华三设备一次展示全部
    private static final String sreanDFF = "screen-length disable\n";
    //华三查询命令
    private static final String collectPortDFF = "display lldp neighbor-information list\n";
    //华为查询命令
    private static final String collectPort = "display lldp neighbor brief\n";
    //华为截取规则
    private static final String huaWeiSplit = "<S";
    //华三截取规则
    private static final String huaSanSplit = "<s";
    /**
     * @param hostport
     * @param user
     * @param password
     * @Description 构造方法
     * @Author wuyufei
     * @Date 2020/9/3 8:55
     * @Param * @param hostname
     * @Return
     * @Exception
     */
    public TelnetBean(String hostname, int hostport, String user,
                      String password) throws SocketException, IOException {
        super();
        this.hostname = hostname;
        this.hostport = hostport;
        this.user = user;
        this.password = password;
        //ZTN
        telnet = new TelnetClient("VT220");// VT100 VT52 VT220 VTNT ANSI
        //拼接telnet命令ip+端口
        telnet.connect(hostname, hostport);
        //获取输入流
        in = telnet.getInputStream();
        //获取输入命令后的输出流
        out = new PrintStream(telnet.getOutputStream());
        //telnet登录后获取输出流（字符串包含）
        readUntil("Username:");
        //控制台输入命令（用户名）
        write(user);
        //回车执行命令
        write("\n");
        //获取输入流（字符串包含）
        readUntil("Password:");
        //控制台输入命令（密码）
        write(password);
        //回车执行命令
        write("\n");
    }
    public void readToEnd() {
        ReadThread readThread = new ReadThread();
        readThread.start();
        try {
            readThread.join();
        } catch (Exception e) {
            logger.info("读到指定行末尾失败");
        }
        //   readThread = null;
    }

    //按/截取
    public String readUntil(String str) {
        // char last = str.charAt(str.length() - 1);
        //String[] ss;
        try {
            StringBuffer sb = new StringBuffer();
            char c;
            int code = -1;
            boolean ansiControl = false;
            boolean start = true;
            StringBuilder stringBuilder = new StringBuilder();
            while ((code = (in.read())) != -1) {
                c = (char) code;
                if (c == '\033') {//vt100控制码都是以\033开头的。
                    ansiControl = true;
                    int code2 = in.read();
                    char cc = (char) code2;
                    if (cc == '[' || cc == '(') {
                    }
                }
                if (!ansiControl) {
                    if (c == '\r') {
                        //这里是命令行中的每一句反馈
                        String olds = new String(sb.toString().getBytes(
                                ORIG_CODEC), TRANSLATE_CODEC);
                        if (olds.contains("/")) {
                            stringBuilder.append(olds).append("\n");
                        }
                        if (sb.lastIndexOf(str) != -1) {
                            break;
                        }
                        sb.delete(0, sb.length());
                    } else if (c == '\n')
                        ;
                    else
                        sb.append(c);
                    if (sb.lastIndexOf(str) != -1) {
                        break;
                    }
                }
                if (ansiControl) {
                    if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')
                            || c == '"') {
                        ansiControl = false;
                    }
                }
            }
            System.out.println(new String(sb.toString().getBytes(ORIG_CODEC), TRANSLATE_CODEC));
            return stringBuilder.toString();

        } catch (Exception e) {
            logger.info("按行读取失败");
        }
        return null;
    }

    //判断型号
    public String readSplit(String str) {
        // char last = str.charAt(str.length() - 1);
        //String[] ss;
        try {
            StringBuffer sb = new StringBuffer();
            char c;
            int code = -1;
            boolean ansiControl = false;
            boolean start = true;
            while ((code = (in.read())) != -1) {
                c = (char) code;
                if (c == '\033') {//vt100控制码都是以\033开头的。
                    ansiControl = true;
                    int code2 = in.read();
                    char cc = (char) code2;
                    if (cc == '[' || cc == '(') {
                    }
                }
                if (!ansiControl) {
                    if (c == '\r') {
                        //这里是命令行中的每一句反馈
                        String olds = new String(sb.toString().getBytes(
                                ORIG_CODEC), TRANSLATE_CODEC);
                        if (sb.lastIndexOf(str) != -1) {
                            break;
                        }
                        sb.delete(0, sb.length());
                    } else if (c == '\n')
                        ;
                    else
                        sb.append(c);
                    if (sb.lastIndexOf(str) != -1) {
                        break;
                    }
                }
                if (ansiControl) {
                    if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')
                            || c == '"') {
                        ansiControl = false;
                    }
                }
            }
            return new String(sb.toString().getBytes(ORIG_CODEC), TRANSLATE_CODEC);
        } catch (Exception e) {
            logger.info("按行读取失败");
        }
        return null;
    }

    public void write(String s) {
        try {
            out.write(s.getBytes());
            //管道挤压得到输出流
            out.flush();
            //  System.out.println(s);
        } catch (Exception e) {
            logger.info("执行命令错误");
        }
    }

    public void write(String s, int sleep) {
        write(s);
        try {
            Thread.sleep(sleep);
        } catch (Exception e) {
            logger.info("执行命令错误");
        }
    }


    /**
     * 完成之后必须关闭
     */
    public void close() {
        if (out != null)
            out.close();
        if (in != null)
            try {
                in.close();
            } catch (IOException e1) {
                logger.info("无法关闭流");
            }
        if (telnet != null)
            try {
                telnet.disconnect();
            } catch (IOException e) {
                logger.info("无法断开telnet连接");
            }
    }

    public String doJob(String hostName, String usrName, String password) {
        return counter(hostName, usrName, password);
    }

    /**
     * @param usrName
     * @param password
     * @Description 嵌套查询
     * @Author wuyufei
     * @Date 2020/9/11 9:15
     * @Param * @param hostName
     * @Return java.lang.String
     * @Exception
     */
    private String counter(String hostName, String usrName, String password) {
        //演示在一台机器上远程登录另一台计算机
        TelnetBean helper = null;
        try {
            //登陆子跳板机
            write("telnet " + hostName + "\n");
            //判断型号
            if (hostName.equals("10.1.81.9") || hostName.equals("10.1.81.10")) {
                //根据型号过滤登陆名
                readUntil("login:");
                return hausanColl(usrName, password);
            } else {
                readUntil("Username:");
                return huaweiColl(usrName, password);
            }

        } catch (Exception e) {
            logger.info("返回参数失败");
        } finally {
            //退出子跳板机
            write("quit\n");
            //退出父跳板机
            write("quit\n");
        }
        return null;
    }

    //华为设备
    public String huaweiColl(String usrName, String password) {
        write(usrName + "\n");
        readUntil("Password:");
        write(password + "\n");
        //读到指定字符串末尾
        readUntil(huaWeiSplit);
        //全部显示
        write(srean);
        readUntil(huaWeiSplit);
        //读到指定字符串末尾
        write(collectPort);
        String str1 = readUntil(huaWeiSplit);
        //返回命令
        readToEnd();
        return str1;
    }

    //华三设备
    public String hausanColl(String usrName, String password) {
        write(usrName + "\n");
        readUntil("Password:");
        write(password + "\n");
        //读到指定字符串末尾
        readUntil(huaSanSplit);
        //全部显示
        write(sreanDFF);
        readUntil(huaSanSplit);
        //读到指定字符串末尾
        write(collectPortDFF);
        String str1 = readUntil(huaSanSplit);
        //返回命令
        readToEnd();
        return str1;
    }

    /**
     * @Description telnet采集指标
     * @Author wuyufei
     * @Date 2020/8/28 14:09
     * @Param * @param null
     * @Return
     * @Exception
     */
    public String collectRountCsco(String hostName, String usrName, String telnetPassword) {
        TelnetBean helper = null;
        try {
            helper = new TelnetBean(hostname, hostport, user, password);
            return helper.doJob(hostName, usrName, telnetPassword);
        } catch (Exception e) {
            logger.info("采集失败");
        } finally {
            if (helper != null) {
                helper.close();
            }
        }
        return null;
    }

    /**
     * 读取主线程，负责管理子线程。防止读取时不动了，这时就抛弃读取子线程 *
     */
    class ReadThread extends Thread {
        public void run() {
            synchronized (lock) {//只能一个读取
                SubReadThread sub = new SubReadThread();
                sub.start();
                int last = sub.count;
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        logger.info("无法使子线程睡眠");
                    }
                    if (last == sub.count) {
                        sub.stop();
                        break;
                    } else {
                        last = sub.count;
                    }
                }
                String s = sub.sb.toString();
                try {
                    System.out.println(new String(s.getBytes(ORIG_CODEC),
                            TRANSLATE_CODEC));
                } catch (UnsupportedEncodingException e) {
                    logger.info(s);
                }
                //   sub = null;
            }
        }
    }

    /**
     * 读取子线程，完成实际读取 *
     */
    class SubReadThread extends Thread {
        int count = 0;
        StringBuffer sb = new StringBuffer();

        public void read() {
            try {
                char c;
                int code = -1;
                boolean ansiControl = false;
                boolean start = true;
                while ((code = (in.read())) != -1) {
                    count++;
                    c = (char) code;
                    if (c == '\033') {
                        ansiControl = true;
                        int code2 = in.read();
                        char cc = (char) code2;
                        count++;
                        if (cc == '[' || cc == '(') {
                        }
                    }
                    if (!ansiControl) {
                        if (c == '\r') {
                            String olds = new String(sb.toString().getBytes(
                                    ORIG_CODEC), TRANSLATE_CODEC);
                            System.out.println(olds);
                            sb.delete(0, sb.length());
                        } else if (c == '\n')
                            ;
                        else
                            sb.append(c);
                    }

                    if (ansiControl) {
                        if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')
                                || c == '"') {
                            ansiControl = false;
                        }
                    }
                }
            } catch (Exception e) {
                logger.info("无法读取参数信息");
            }
        }

        public void run() {
            read();
        }
    }
}
