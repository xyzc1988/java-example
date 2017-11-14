package com.etoak.test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by zhangcheng on 2017/11/14 11:44.
 */
public class AutoCloseTest {
    public static void main(String[] args)  {
        URL resource = AutoCloseTest.class.getResource("/try-with-resource.txt");
        testNormalOutput(resource.getPath());
        testAutoCloseWithTryCatch(resource.getPath());
    }

    private static void testNormalOutput(String filepath){
        OutputStream global_out = null;
        BufferedWriter writer ;
        try {
            OutputStream out = out = new FileOutputStream(filepath);
            global_out = out;
            out.write((filepath + "inside try catch block").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            if(global_out!=null){
                global_out.write("  \t\r outside try catch block".getBytes());
                global_out.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testAutoCloseWithTryCatch(String filepath){
        OutputStream global_out = null;
        try(OutputStream out = new FileOutputStream(filepath);) {
            global_out = out;
            out.write((filepath+"inside try catch block").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用try-with-resources资源已经关闭
        try{
            if(global_out!=null){
                global_out.write("  \t\r outside try catch block".getBytes());
                global_out.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}