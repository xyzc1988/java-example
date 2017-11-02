package com.etoak.WriterQueue;

import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangcheng on 2016/7/26.
 */
public class TestWrite {

    public static void main(String[] args) {
        try {
            PrintWriter pw = new PrintWriter("d:/test.txt");
            for(int i = 0;i<10000;i++){
                int temp = (int) (Math.random() * 100000);
                pw.println(temp);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

class Task extends Thread{
    private int oddNum = 0;
    private int evenNum = 0;

    public void run(){
       // BufferedReader br = new BufferedReader("d:/test.txt");
    }

}