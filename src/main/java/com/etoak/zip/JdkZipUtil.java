package com.etoak.zip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 基于JDK的ZIP工具类，支持中文文件的压缩
 *
 * @author zhangcheng
 */
public class JdkZipUtil {

    private static Logger logger = LoggerFactory.getLogger(JdkZipUtil.class);

    private static int BUFFER_SIZE = 2048;//size of bytes
    private byte[] buffer;
    private int readedBytes;

    public JdkZipUtil() {
        this(BUFFER_SIZE);
    }

    public JdkZipUtil(int bufSize) {
        this.buffer = new byte[bufSize];
    }

    /**
     * 压缩文件夹内的文件
     *
     * @param src  需要压缩的文件夹名
     * @param dest 生成zip文件全路径名
     */
    public void makeZip(String src, String dest) {
        File sourceFile = new File(src);
        try (ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(dest)))) {
            doZipFile(sourceFile, zipOut, sourceFile.getName());
        } catch (IOException ioe) {
            logger.error("文件压缩发生异常:", ioe);
        }
    }

    /**
     * 由doZip调用,递归完成目录文件读取
     *
     * @param srcFile
     * @param zipOut
     * @param zipForderPath zip内部创建的目录
     * @throws IOException
     */
    private void doZipFile(File srcFile, ZipOutputStream zipOut, String zipForderPath) throws IOException {
        if (srcFile.isFile()) {
            try (FileInputStream fileIn = new FileInputStream(srcFile)) {
                zipOut.putNextEntry(new ZipEntry(zipForderPath));
                while ((this.readedBytes = fileIn.read(this.buffer)) > 0) {
                    zipOut.write(this.buffer, 0, this.readedBytes);
                }
            } catch (IOException e) {
                throw e;
            } finally {
                zipOut.closeEntry();
            }

        } else {
            for (File file : srcFile.listFiles()) {
                doZipFile(file, zipOut, zipForderPath + "/" + file.getName());
            }
        }
    }

    /**
     * 解压指定zip文件
     *
     * @param unZipfileName 需要解压的zip文件名
     * @param target        解压目标文件夹
     */
    public void unZip(String unZipfileName, String target) {//unZipfileName
        File file;
        ZipEntry zipEntry;
        try (ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(unZipfileName)))) {
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                file = new File(target, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    //如果指定文件的目录不存在,则创建之.
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    try (FileOutputStream fileOut = new FileOutputStream(file);) {
                        while ((this.readedBytes = zipIn.read(this.buffer)) > 0) {
                            fileOut.write(this.buffer, 0, this.readedBytes);
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                }
                zipIn.closeEntry();
            }
        } catch (IOException ioe) {
            logger.error("文件解压发生异常:", ioe);
        }
    }


    public static void main(String[] args) {
        JdkZipUtil unZipPag = new JdkZipUtil();
        unZipPag.unZip("D:\\target.zip","d:\\邮件解析\\target");
        //unZipPag.makeZip("D:\\usr", "d:\\target.zip");
    }
}
