package com.etoak.nio;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

/**
 * Created by zhangcheng on 2016/5/23.
 */
public class TestChannelWrite {
    /*static private final byte message[] = { 83, 111, 109, 101, 32,
            98, 121, 116, 101, 115, 46 };*/
    private static final String message = "ceshi";
    static public void main( String args[] ) throws Exception {
        FileOutputStream fout = new FileOutputStream( "d:\\data\\test.txt" );

        FileChannel fc = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate( 1024 );

        byte messages[] = message.getBytes();

        for (int i=0; i<messages.length; ++i) {
            buffer.put( messages[i] );
        }

        buffer.flip();

        fc.write( buffer );

        fout.close();
    }
}