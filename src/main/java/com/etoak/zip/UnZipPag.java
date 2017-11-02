package com.etoak.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解压zip包的工具类，支持中文文件的压缩
 * 
 * @author zhangcheng
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UnZipPag {
	private static Logger logger = LoggerFactory.getLogger(UnZipPag.class);

	/** key：原始名称；value：解压后带路径的文件名称 */
	private Map<String, String> filePathMap = new HashMap<String, String>();

	static final int BUFFER = 2048;  

	public void unzip(String zipFileName, String tarFilePath) throws Exception {
		InputStream in = null;
		FileOutputStream out = null;
		ZipFile zipFile = null;
		BufferedOutputStream bos = null;  
		try {
			zipFile = new ZipFile(zipFileName);
			  //创建输入字节流  
            FileInputStream fis = new FileInputStream(zipFileName);  
            //根据输入字节流创建输入字符流  
            BufferedInputStream bis = new BufferedInputStream(fis);  
            //根据字符流，创建ZIP文件输入流  
            ZipInputStream zis = new ZipInputStream(bis);  
            //zip文件条目，表示zip文件  
            ZipEntry zipEntry;  
            //循环读取文件条目，只要不为空，就进行处理  
			while ((zipEntry=zis.getNextEntry())!=null) {
				try {
					logger.debug("上传压缩包内的条目名称是:" + zipEntry.getName());
					zipEntry.getName().lastIndexOf("/");
					int count ;  
	                byte date[] = new byte[BUFFER];  
	                //如果条目是文件目录，则继续执行  
	                if(zipEntry.isDirectory()){  
	                    continue;  
	                }else{  
	                    int begin = zipFileName.lastIndexOf("\\")+1;  
	                    int end = zipFileName.lastIndexOf(".")+1;  
	                    zipFileName.substring(begin,end);  
	                    bos = new BufferedOutputStream(new FileOutputStream(this.getRealFileName(tarFilePath,zipEntry.getName())));  
	                    while((count=zis.read(date))!=-1){  
	                        bos.write(date,0,count);  
	                    }  
	                    bos.flush();
	                    bos.close();  
	                }
					
				} catch (Exception e1) {
					logger.error("io exception occur when unzip file", e1);
					throw new Exception("系统繁忙，请联系管理员");
				} finally {
					IOUtils.closeQuietly(out);
					IOUtils.closeQuietly(in);
				}
			}
		} catch (IOException e1) {
			logger.error("io exception occur when unzip file", e1);
			throw new Exception("zip解压失败，请上传正确格式zip文件");
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
			if (null != zipFile) {
				try {
					zipFile.close();
				} catch (IOException e) {
					logger.error("io exception occur when unzip file", e);
					throw new Exception("系统繁忙，请联系管理员");
				}
			}
		}
	}
	 private File getRealFileName(String zippath,String absFileName){  
	        String[] dirs = absFileName.split("/",absFileName.length());  
	        //创建文件对象   
	        File file = new File(zippath);  
	        if(dirs.length>1){  
	            for(int i=0;i<dirs.length-1;i++){  
	                //根据file抽象路径和dir路径字符串创建一个新的file对象，路径为文件的上一个目录  
	                file = new File(file,dirs[i]);  
	            }  
	        }  
	        if(!file.exists()){  
	            file.mkdirs();  
	        }  
	        file = new File(file,dirs[dirs.length-1]); 
	        String tarFilePathWithName = zippath + "/"
					+ absFileName;
	        filePathMap.put(absFileName,
					tarFilePathWithName);
	        return file;  
	    }  


	public Map<String, String> getFilePathMap() {
		return filePathMap;
	}
}
