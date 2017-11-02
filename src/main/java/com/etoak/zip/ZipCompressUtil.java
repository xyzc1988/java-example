package com.etoak.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用ZIP压缩和解压缩文件或目录
 */
public class ZipCompressUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZipCompressUtil.class);

	/**
	 * 压缩文件或者目录
	 * 
	 * @param baseDirName
	 *            压缩的根目录
	 * @param fileName
	 *            根目录下待压缩的文件或文件夹名， 星号*表示压缩根目录下的全部文件。
	 * @param targetFileName
	 *            目标ZIP文件
	 */
	public static void zipFile(String baseDirName, String fileName,
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
		String baseDirPath = baseDir.getAbsolutePath();
		// 目标文件
		File targetFile = new File(targetFileName);

		ZipOutputStream out = null;
		try {
			// 创建一个zip输出流来压缩数据并写入到zip文件
			out = new ZipOutputStream(new FileOutputStream(targetFile));
			if (fileName.equals("*")) {
				// 将baseDir目录下的所有文件压缩到ZIP
				ZipCompressUtil.dirToZip(baseDirPath, baseDir, out);
			} else {
				File file = new File(baseDir, fileName);
				if (file.isFile()) {
					ZipCompressUtil.fileToZip(baseDirPath, file, out);
				} else {
					ZipCompressUtil.dirToZip(baseDirPath, file, out);
				}
			}

			logger.info("压缩文件成功，目标文件名：" + targetFileName);
		} catch (IOException e) {
			logger.error("压缩失败：" + e);
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("关闭输出流失败：" + e);
				}
			}

		}
	}

	/**
	 * 解压缩ZIP文件，将ZIP文件里的内容解压到targetDIR目录下
	 * 
	 * @param zipName
	 *            待解压缩的ZIP文件名
	 * @param targetBaseDirName
	 *            目标目录
	 */
	public static List<String> upzipFile(String zipFileName,String targetBaseDirName) throws Exception {
		if (!targetBaseDirName.endsWith(File.separator)) {
			targetBaseDirName += File.separator;
		}

		// 文件输出流
		FileOutputStream os = null;

		List<String> filename = new ArrayList<String>();

		// 文件输入流
		InputStream is = null;
		ZipFile zipFile = null;
		try {
			// 根据ZIP文件创建ZipFile对象
			zipFile = new ZipFile(zipFileName, "GB18030");
			ZipEntry entry = null;
			String entryName = null;
			String targetFileName = null;
			byte[] buffer = new byte[4096];
			int bytes_read;

			// 获取ZIP文件里所有的entry
			Enumeration entrys = zipFile.getEntries();

			// 遍历所有entry
			while (entrys.hasMoreElements()) {
				try {
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
					System.out.println("创建文件：" + targetFile.getAbsolutePath());

					// 打开文件输出流
					os = new FileOutputStream(targetFile);

					// 从ZipFile对象中打开entry的输入流
					is = zipFile.getInputStream(entry);
					while ((bytes_read = is.read(buffer)) != -1) {
						os.write(buffer, 0, bytes_read);
					}

					filename.add(targetFileName);
				} catch (Exception e1) {
					logger.error("解压缩文件失败！", e1);
					throw e1;
				} finally {
					IOUtils.closeQuietly(os);
					IOUtils.closeQuietly(is);
				}
			}
			logger.info("解压缩文件成功！");
		} catch (IOException err) {
			logger.error("解压缩文件失败: " + err);
			throw err;
		} finally {

			try {
				if (null != os) {
					os.close();
				}

				if (null != is) {
					is.close();
				}

				if (null != zipFile) {
					zipFile.close();
				}

			} catch (IOException e) {
				logger.error("关闭失败：" + e);
			}
		}
		return filename;
	}

	/**
	 * 将目录压缩到ZIP输出流。
	 */
	private static void dirToZip(String baseDirPath, File dir,
			ZipOutputStream out) {
		if (dir.isDirectory()) {
			// 列出dir目录下所有文件
			File[] files = dir.listFiles();
			// 如果是空文件夹
			if (files.length == 0) {
				ZipEntry entry = new ZipEntry(getEntryName(baseDirPath, dir));
				// 存储目录信息
				try {
					out.putNextEntry(entry);
					out.closeEntry();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					// 如果是文件，调用fileToZip方法
					ZipCompressUtil.fileToZip(baseDirPath, files[i], out);
				} else {
					// 如果是目录，递归调用
					ZipCompressUtil.dirToZip(baseDirPath, files[i], out);
				}
			}
		}
	}

	/**
	 * 将文件压缩到ZIP输出流
	 */
	private static void fileToZip(String baseDirPath, File file,
			ZipOutputStream out) {
		FileInputStream in = null;
		ZipEntry entry = null;
		// 创建复制缓冲区
		byte[] buffer = new byte[4096];
		int bytes_read;
		if (file.isFile()) {
			try {
				// 创建一个文件输入流
				in = new FileInputStream(file);
				// 做一个ZipEntry
				entry = new ZipEntry(getEntryName(baseDirPath, file));
				// 存储项信息到压缩文件
				out.putNextEntry(entry);
				// 复制字节到压缩文件
				while ((bytes_read = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytes_read);
				}
				out.closeEntry();
				in.close();
				logger.info("添加文件" + file.getAbsolutePath() + "被到ZIP文件中！");
			} catch (IOException e) {
				logger.error("添加文件失败：" + e);
			}
		}
	}

	/**
	 * 获取待压缩文件在ZIP文件中entry的名字。即相对于跟目录的相对路径名
	 * 
	 * @param baseDirPath
	 * @param file
	 * @return
	 */
	private static String getEntryName(String baseDirPath, File file) {
		if (!baseDirPath.endsWith(File.separator)) {
			baseDirPath += File.separator;
		}
		String filePath = file.getAbsolutePath();
		// 对于目录，必须在entry名字后面加上"/"，表示它将以目录项存储。
		if (file.isDirectory()) {
			filePath += "/";
		}
		int index = filePath.indexOf(baseDirPath);
		return filePath.substring(index + baseDirPath.length());
	}

	public static void main(String[] args) {
		// 压缩C盘下的temp目录，压缩后的文件是C:/temp.zip
		String baseDirName = "C:/";
		String fileName = "temp/";
		String zipFileName = "C:/temp.zip";
		//ZipCompressUtil.zipFile(baseDirName, fileName, zipFileName);
		// 将刚创建的ZIP文件解压缩到D盘的temp目录下
		System.out.println();
		fileName = "D:/temp";
		try {
			ZipCompressUtil.upzipFile("C:\\Users\\zhangcheng\\Desktop\\临时\\导入中文.zip", "D:\\operationUsergroup");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
