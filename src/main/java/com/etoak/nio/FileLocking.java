package com.etoak.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangcheng on 2016/10/9.
 */
public class FileLocking {
    public static void main(String[] args) throws IOException, InterruptedException {
        FileOutputStream fos = new FileOutputStream("file.txt");
        FileLock fileLock = fos.getChannel().tryLock();
        if (fileLock != null) {
            System.out.println("Lock File!");
            TimeUnit.SECONDS.sleep(1);
            fileLock.release();
            System.out.println("Release Lock!");
        }
        fos.close();
    }
}
