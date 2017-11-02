package com.etoak.nio;

import com.sun.beans.editors.ByteEditor;

import java.nio.ByteBuffer;

/**
 * Created by zhangcheng on 2016/10/8.
 */
public class GetData {
    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        int i = 0;
        while (i++ < buffer.limit()) {
            if (buffer.get() != 0) {
                System.out.println("nozero");
            }
            System.out.println("i = " + i);
        }
        buffer.rewind();
        buffer.asCharBuffer().put("Howdy!");
        char c;
        while ((c = buffer.getChar()) != 0) {
            System.out.println(c + "");
        }
        buffer.rewind();

        buffer.asShortBuffer().put((short) 471142);
        System.out.println(buffer.getShort());
        buffer.rewind();

        buffer.asIntBuffer().put(99471142);
        System.out.println(buffer.getInt());
        buffer.rewind();

    }
}
