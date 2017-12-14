package com.etoak.nio;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by zhangcheng on 2016/9/26.
 */
public class GetChannel {

    private static final int BSIZE = 1024;

    public static void main(String[] args) throws IOException {
        String classPath = GetChannel.class.getClassLoader().getResource("").getPath();
        FileChannel fileChannel = new FileOutputStream(classPath + "data.txt").getChannel();
        fileChannel.write(ByteBuffer.wrap("苹果".getBytes()));
        fileChannel.close();

        fileChannel = new RandomAccessFile(classPath + "data.txt", "rw").getChannel();
        fileChannel.position(fileChannel.size());
        fileChannel.write(ByteBuffer.wrap("香蕉".getBytes()));
        fileChannel.close();

        fileChannel = new FileInputStream(classPath + "data.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        fileChannel.read(buffer);
        buffer.flip();
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }

    }
}
