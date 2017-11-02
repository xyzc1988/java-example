package com.etoak.thread;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhangcheng on 2016/5/30.
 */
public class QueueTest {


    public static void main(String[] args) throws InterruptedException {


        LinkedBlockingQueue bq = new LinkedBlockingQueue();
        bq.add(10);
        bq.add(11);
        System.out.println(bq.take());
        System.out.println(bq.size());
    }
}

