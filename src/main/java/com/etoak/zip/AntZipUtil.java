package com.etoak.zip;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用ZIP压缩和解压缩文件或目录
 */
public class AntZipUtil {
    private static final Logger logger = LoggerFactory.getLogger(AntZipUtil.class);

    /**
     * 压缩文件或者目录
     *
     * @param baseDirName    压缩的根目录
     * @param fileName       根目录下待压缩的文件或文件夹名， 星号*表示压缩根目录下的全部文件。
     * @param targetFileName 目标ZIP文件
     */
    public static void makeZip(String baseDirName, String fileName,
                               String targetFileName) {

        // 检测根目录是否存在
        if (baseDirName == null) {
            logger.error("压缩失败，根目录不存在：" + baseDirName);
            return;
        }
        File baseDir = new File(baseDirName);
        if (!baseDir.exists() || (!baseDir.isDirectory())) {
            logger.error("压缩失败，根目录不存在：" + baseDirName);
            return;
        }
        // 创建一个zip输出流来压缩数据并写入到zip文件
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetFileName));) {

            File file = new File(baseDir, fileName);
            doZipFile(file, out, file.getName());
            logger.info("压缩文件成功，目标文件名：" + targetFileName);
        } catch (IOException e) {
            logger.error("压缩失败：" + e);
        }
    }

    private static void doZipFile(File srcFile, ZipOutputStream zipOut, String zipForderPath) throws IOException {
        if (srcFile.isFile()) {
            try (FileInputStream fileIn = new FileInputStream(srcFile)) {
                zipOut.putNextEntry(new ZipEntry(zipForderPath));
                byte[] buffer = new byte[4096];
                int len;
                while ((len = fileIn.read(buffer)) > 0) {
                    zipOut.write(buffer, 0, len);
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
     * 解压缩ZIP文件，将ZIP文件里的内容解压到targetDIR目录下
     *
     * @param zipName           待解压缩的ZIP文件名
     * @param targetBaseDirName 目标目录
     */
    public static List<String> upzipFile(String zipFileName, String targetBaseDirName) throws Exception {
        if (!targetBaseDirName.endsWith(File.separator)) {
            targetBaseDirName += File.separator;
        }

        List<String> filename = new ArrayList<String>();

        ZipFile zipFile = null;
        try {
            // 根据ZIP文件创建ZipFile对象
            zipFile = new ZipFile(zipFileName, "GB18030");
        } catch (IOException err) {
            logger.error("解压缩文件失败: " + err);
            throw err;
        }

        ZipEntry entry = null;
        String entryName = null;
        String targetFileName = null;
        byte[] buffer = new byte[4096];
        int bytes_read;

        // 获取ZIP文件里所有的entry
        Enumeration entrys = zipFile.getEntries();

        // 遍历所有entry
        while (entrys.hasMoreElements()) {

            entry = (ZipEntry) entrys.nextElement();

            // 获得entry的名字
            entryName = entry.getName();
            targetFileName = targetBaseDirName + entryName;
            if (entry.isDirectory()) {
                // 如果entry是一个目录，则创建目录
                new File(targetFileName).mkdirs();
                continue;
            } else {
                // 如果entry是一个文件，则创建父目录
                new File(targetFileName).getParentFile().mkdirs();
            }

            // 否则创建文件
            File targetFile = new File(targetFileName);

            try (
                    // 打开文件输出流
                    FileOutputStream os = new FileOutputStream(targetFile);
                    // 从ZipFile对象中打开entry的输入流
                    InputStream is = zipFile.getInputStream(entry)) {
                while ((bytes_read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytes_read);
                }
                filename.add(targetFileName);
            } catch (IOException e) {
                logger.error("解压缩文件失败！", e);
                throw e;
            }

        }
        logger.info("解压缩文件成功！");

        return filename;
    }


    public static void main(String[] args) {
        // 压缩C盘下的temp目录，压缩后的文件是C:/temp.zip
        String baseDirName = "C:/";
        String fileName = "temp/";
        String zipFileName = "C:/temp.zip";
        //AntZipUtil.makeZip(baseDirName, fileName, zipFileName);
        // 将刚创建的ZIP文件解压缩到D盘的temp目录下
        System.out.println();
        fileName = "D:/temp";
        try {
            AntZipUtil.upzipFile("C:\\Users\\zhangcheng\\Desktop\\临时\\导入中文.zip", "D:\\operationUsergroup");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



}
