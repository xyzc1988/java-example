package com.etoak.test;

import java.io.*;
import java.util.*;
import java.text.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//文本处理包
public class TestTime {
    private static Logger logger = LoggerFactory.getLogger(TestTime.class);

    public static void main(String[] args) {
        File dir = new File("123.jpg");
        long time = dir.lastModified();//最后一次修改时间
        //System.out.println(time);
        //如何将时间戳的毫秒数 转换成 年月日 时分秒
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ok = s.format(time);
        System.out.println(ok);

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        System.out.println(s.format(new Date()));
        System.out.println("====================================");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());//设置时间
        cal.setTimeInMillis(System.currentTimeMillis());
        //System.out.println(cal);

        System.out.println("1-年-" + cal.get(1));//年
        System.out.println("2-月-" + (cal.get(2) + 1));//月
        System.out.println("5-日-" + cal.get(5));//日
        System.out.println("11-时-" + cal.get(11));//时
        System.out.println("12-分-" + cal.get(12));//分
        System.out.println("13-秒-" + cal.get(13));//秒
        System.out.println("====================================");

        System.out.println("1-年-" + cal.get(Calendar.YEAR));//年
        System.out.println("2-月-" + (cal.get(Calendar.MONTH) + 1));//月
        System.out.println("5-日-" + cal.get(Calendar.DAY_OF_MONTH));//日
        System.out.println("11-时-" + cal.get(Calendar.HOUR));//时
        System.out.println("12-分-" + cal.get(Calendar.MINUTE));//分
        System.out.println("13-秒-" + cal.get(Calendar.SECOND));//秒


	/*	System.out.println("====================================");
		Date now = new Date();//当前时间
		System.out.println(now);
		System.out.println(now.getYear()+1900);
		System.out.println(now.getMonth()+1);
		System.out.println(now.getDate());
		System.out.println(now.getHours());
		System.out.println(now.getMinutes());
		System.out.println(now.getSeconds());*/
    }
}