package com.etoak.nio;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by zhangcheng on 2016/10/8.
 */
public class MappedIO {
    private static int numOfInts = 4000000;
    private static int numOfUbuffInts = 200000;
    private abstract static class Tester{
        private String name;
        public Tester (String name){
            this.name = name;
        }
        public void runTest(){
            System.out.println(name + ": ");
            try{
                long start = System.nanoTime();
                test();
                double duration = System.nanoTime() -start;
                System.out.format("%.2f\n",duration/1.0e9);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        public abstract void test() throws IOException;
    }

    private static Tester[] tests = {
            new Tester("Stream write") {
                @Override
                public void test() throws IOException {
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("temp.tmp")));
                    for (int i = 0;i< numOfInts;i++) {
                        dos.writeInt(i);
                    }
                    dos.close();
                }
            },
            new Tester("Mapped write") {
                @Override
                public void test() throws IOException {
                    FileChannel fc = new RandomAccessFile("temp.tmp","rw").getChannel();
                    IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
                    for (int i = 0;i<numOfInts;i++) {
                        ib.put(i);
                    }
                    fc.close();
                }
            },
            new Tester("Stream Read") {
                @Override
                public void test() throws IOException {
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream("temp.tmp")));
                    for (int i = 0;i<numOfInts;i++) {
                        dis.readInt();
                    }
                    dis.close();
                }
            },
            new Tester("Mapped Read") {
                @Override
                public void test() throws IOException {
                    FileChannel fc = new RandomAccessFile("temp.tmp","rw").getChannel();
                    IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
                    while (ib.hasRemaining()) {
                        ib.get();
                    }
                }
            }
    };

    public static void main(String[] args) {
        for(Tester tester : tests){
            tester.runTest();
        }
    }
}
