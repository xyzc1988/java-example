package com.etoak.WriterQueue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

/**
 * Created by zhangcheng on 2016/7/26.
 */
public class OutputTask implements Runnable {
    private String fileName;

    public OutputTask(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public void run() {

        while(true){
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LinkedList<String> list = WriterQueue.getQueue().takeAll();
            write2Disk(list);
            list = null;
        }
    }

    private void write2Disk(LinkedList<String> list) {

        if(list==null ||list.size()==0){
            System.out.println("no data...");
            return;
        }

        System.out.println("开始序列化数据 "+fileName);

        String path = "D:/fbb/myWorkSpace_DW07/";
        File outputFile = new File(path+fileName);

        if(outputFile==null ||!outputFile.exists()){
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream out = null;
        OutputStreamWriter writer = null;
        BufferedWriter bw = null;

        try {
            out = new FileOutputStream(outputFile, true);
            writer = new OutputStreamWriter(out);
            bw = new BufferedWriter(writer);

            for(String content : list){
                bw.write(content);
                bw.newLine();
                bw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(bw!=null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sleep(int millis) throws InterruptedException {
        Thread.sleep(millis);
    }

}