package com.etoak.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by zhangcheng on 2016/9/28.
 */
public class BufferToText {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws IOException {
        FileChannel fc = new FileOutputStream("data2.txt").getChannel();
        fc.write(ByteBuffer.wrap("Some text".getBytes()));
        fc.close();

        fc = new FileInputStream("data2.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        fc.read(buffer);
        buffer.flip();
        //不可行
        System.out.println(buffer.asCharBuffer());
        //使用系统默认编码方式进行解码
        buffer.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("Decoded using " + encoding + " : " + Charset.forName(encoding).decode(buffer));

        //或者我们使用要打印的字符集进行编码
        fc = new FileOutputStream("data2.txt").getChannel();
        fc.write(ByteBuffer.wrap("Some text".getBytes("UTF-16BE")));
        fc.close();

        fc = new FileInputStream("data2.txt").getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());

        fc = new FileOutputStream("data2.txt").getChannel();
        buffer = ByteBuffer.allocate(24);
        buffer.asCharBuffer().put("Some text");
        fc.write(buffer);
        fc.close();

        fc = new FileInputStream("data2.txt").getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());

        CharBuffer charBuffer = buffer.asCharBuffer();
        while (charBuffer.hasRemaining()){
            System.out.println(charBuffer.get());
        }
    }
}
