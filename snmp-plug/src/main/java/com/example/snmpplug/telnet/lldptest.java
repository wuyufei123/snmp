package com.example.snmpplug.telnet;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.awt.print.Pageable;
import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class lldptest {


    public static void main(String[] args) {
        long starttime=System.currentTimeMillis();
        ArrayList<String> outList=new ArrayList<>();
        ArrayList<String> passList=new ArrayList<>();
        //输入第一台设备名
        String nextdev="Sw1";
        String localDev="Sw1";
        String ip="10.1.81.1";
        String username="jsict@default";
        String password="jsict";
        passList.add(nextdev);

        multiRound(nextdev,localDev,ip,username,password,passList,outList);
        distinct(outList);
        for(int i=0;i<outList.size();i++){
            System.out.println(outList.get(i));
        }
        long endtime=System.currentTimeMillis();
        long time=endtime-starttime;
        System.out.println("总耗时："+time);
    }


/*
        循环获取所有设备
     */

    public static String multiRound(String nextDev,String localDev,String ip,String username,String password, List<String> passList,List<String> outList){
        //String filePath="E:\\云网运营系统\\采控平台文档\\lldp网络拓扑\\";
       // String file;
        String lineFormat;
        ArrayList<String> lineList;
        Map<String,String> map =new HashMap<>();
        //获取回显内容
        lineList=getInfo(ip,username,password);

        //结果用local设备名拼接
        for(int i=0;i<lineList.size();i++) {
            map=format(lineList.get(i));
            lineFormat=map.get("lineFormat");
            outList.add(localDev + "  " + lineFormat);
        }

        for(int i=0;i<lineList.size();i++) {
            map=format(lineList.get(i));
            nextDev=map.get("nextDev");

            switch(nextDev){
                case "Sw1":
                    ip="10.1.81.1";
                    username="jsict@default";
                    password="jsict";
                    break;
                case "Sw2":
                    ip="10.1.81.2";
                    username="jsict@default";
                    password="jsict";
                    break;
                case "SW3":
                    ip="10.1.81.3";
                    username="jsict@default";
                    password="jsict";
                    break;
                case "SW4":
                    ip="10.1.81.4";
                    username="jsict@default";
                    password="jsict";
                    break;
                case "SW5":
                    ip="10.1.81.5";
                    username="jsict@default";
                    password="jsict";
                    break;
                case "SW6":
                    ip="10.1.81.6";
                    username="jsict@default";
                    password="jsict";
                    break;
                case "SW7":
                    ip="10.1.81.7";
                    username="jsict@default";
                    password="jsict";
                    break;
                case "Sw8":
                    ip="10.1.81.8";
                    username="jsict@default";
                    password="jsict";
                    break;
                case "sw9":
                    ip="10.1.81.9";
                    username="jsict";
                    password="jsict";
                    break;
                case "sw10":
                    ip="10.1.81.10";
                    username="jsict";
                    password="jsict";
                    break;
                default:
                    break;

            }
            if (!nextDev.equals("")&&!passList.contains(nextDev)){
                if(nextDev.contains("sw")|nextDev.contains("SW")|nextDev.contains("Sw")|nextDev.contains("sW")){
                    passList.add(nextDev);
                    localDev=nextDev;
                    multiRound(nextDev,localDev,ip,username,password,passList,outList);
                }else{
                    continue;
                }
            }else{
                continue;
            }
        }
        return null;
    }


/*
        按行读取brief文件内容
        应用中需替换掉
     */

/*
    public static ArrayList<String> readFile(String txtPath){
        String line="";
        //System.out.println(txtPath+"\n");
        ArrayList<String> lineList= new ArrayList<>();
        try{
            FileReader fr = new FileReader(txtPath);
            BufferedReader bf = new BufferedReader(fr);
            //按行读取
            while ((line = bf.readLine()) != null){
                lineList.add(line);
            }
            bf.close();
            fr.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return lineList;
    }

*/

    /*
        调用telnet登录方法，获取回显内容并按行存入list
     */
    public static ArrayList<String> getInfo(String ip,String username,String password){
        TelnetBean telnetBean=new TelnetBean();
        String info=telnetBean.collectRountCsco(ip,username,password);
        ArrayList<String> lineList= new ArrayList<>();

        String[] str=info.split("\\r?\\n");
        for (int i=0;i<str.length;i++){
            lineList.add(str[i]);
        }
        return lineList;

    }


/*
        截取邻居设备名
     */

    public static String getDev(String line){
        String dev="";
        Pattern p1 = Pattern.compile("(.*)(\\d[a-zA-Z].*)");
        Pattern p2 = Pattern.compile("(Sw|sw|sW|SW)");
        Matcher m1 =p1.matcher(line);
        Matcher m2 =p2.matcher(line);
       if(m1.find()){
           dev=m1.group(2).substring(1);
       }else if (m2.find()) {
           dev=line.substring(m2.start(),line.length());
       }else{
           dev="";
       }

        System.out.println(dev);


        return dev;
    }

    /*
        格式化每行的输出：截取下一设备名，分割端口与设备，去除多余空格
     */
    public static Map<String,String> format(String line){
        String lineFormat;
        String nextDev;
        String temp; //存储形如Ten-GigabitEthernet2/0/48CLL_core的子串
        int flag; //分割设备名的定位
        Map<String,String> map =new HashMap<>();
        lineFormat=line.replace("Ten-GigabitEthernet","XGigabitEthernet");
        lineFormat=lineFormat.replace("10GE","XGigabitEthernet");
        lineFormat=lineFormat.replace("XGE","XGigabitEthernet");
        lineFormat=lineFormat.replaceAll("\\s{1,}", " ");
        //匹配整行格式，分为3空格和4空格
        Pattern fourSpace=Pattern.compile("^(\\S+\\s+\\S+\\s+\\S+\\s+)(\\S+)$");
        Pattern threeSpace=Pattern.compile("^(\\S+\\s+\\S+\\s+)(\\S+)$");
        Matcher fourSpaceMatch=fourSpace.matcher(lineFormat);
        Matcher threeSpaceMatch=threeSpace.matcher(lineFormat);


        //4空格的匹配最后一段为下一设备名，3空格的根据匹配分割截取最后的字符
        Pattern p = Pattern.compile("(.*\\d+/\\d+/\\d+)(.*)");

        if(fourSpaceMatch.find()){
            nextDev=fourSpaceMatch.group(2);
        }else if (threeSpaceMatch.find()){
            temp=threeSpaceMatch.group(2);
            Matcher m = p.matcher(temp);
            if (m.find()){
                nextDev=m.group(2);
                flag=lineFormat.length()-nextDev.length();
                lineFormat=lineFormat.substring(0,flag)+" "+nextDev;
            }else{
                nextDev="";
            }
        }else {
            nextDev="";
        }

        map.put("nextDev",nextDev);
        map.put("lineFormat",lineFormat);

        return map;

    }

    /*
        对结果去重
     */
    public static ArrayList<String> distinct(ArrayList<String> outList){
        String[][] out=new String[outList.size()][5];
        String[] temp;
        List<String> removeList =new ArrayList<>();
        int i,j;
        int n,m;
        //分割存入数组
        for(i=0;i<outList.size();i++){
            temp=outList.get(i).split("\\s+");
            for (j=0;j<temp.length;j++){
                out[i][j]=temp[j];
            }
        }
        System.out.println(out);
        //双重遍历去重
        for(n=0;n<out.length;n++){
            for(m=n;m<out.length;m++){
                if(out[n][0].equals(out[m][4])&&out[n][1].equals(out[m][3])){
                    removeList.add(outList.get(n));
                }else{
                    continue;
                }
            }
        }
        outList.removeAll(removeList);



        return outList;
    }


}

