package com.etoak.nio;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
/**
 * Created by zhangcheng on 2016/5/23.
 */
public class TestChannelRead {
    static public void main( String args[] ) throws Exception {
        FileInputStream fin = new FileInputStream("D:\\data\\phone.txt");

        FileChannel fc = fin.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        fc.read(buffer);

        buffer.flip();

        while (buffer.remaining()>0) {
            byte b = buffer.get();
            System.out.print(((char)b));
        }

        fin.close();
    }
}