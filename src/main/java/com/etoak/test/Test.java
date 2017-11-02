package com.etoak.test;

/**
 * Created by zhangcheng on 2016/12/2.
 */
public class Test {
    public static void main(String[] args) {
        String a = "1--2--3";
        String b = "1--2--2";

        int result=a.compareTo(b);
        if (result==0)
            System.out.println("a=b");
        else if (result < 0 )
            System.out.println("a<b");
        else System.out.println("a>b");
    }
}
