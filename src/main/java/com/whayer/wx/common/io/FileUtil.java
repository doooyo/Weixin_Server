package com.whayer.wx.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whayer.wx.common.X;

public class FileUtil {
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 检查磁盘可用空间大小 byte
	 * 
	 * @param file
	 * @return
	 */
	public static long diskFreeSpace(String file) {
		File f = new File(file);
		if (!f.exists()) {
			return -1;
		}
		return f.getFreeSpace();
	}

	/**
	 * 检查磁盘总大小 byte
	 * 
	 * @param file
	 * @return
	 */
	public static long diskTotalSpace(String file) {
		File f = new File(file);
		if (!f.exists()) {
			return -1;
		}
		return f.getTotalSpace();
	}

	/**
	 * 单位换算
	 * 
	 * @param size
	 * @return
	 */
	public static String fileSizeTransfer(long size) {
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}

		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {

			size = size * 100;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
		} else {

			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
		}
	}

	/**
	 * 获取文件扩展名 自动转小写
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if (dot > -1) {
				return filename.substring(dot + 1).toLowerCase();
			}
		}
		return null;
	}

	/**
	 * 获取不带扩展名的文件名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameWithOutExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if (dot > -1) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 从文件读取String
	 * 
	 * @param file
	 * @return
	 */
	public static String readFileToString(File file, String chartset) {
		try {
			FileInputStream fis = new FileInputStream(file);
			String content = X.stream2String(new FileInputStream(file), chartset);
			X.close(fis);
			return content;
		} catch (Exception e) {
			log.error(null, e);
			return null;
		}
	}

	/**
	 * 将String 写入 文件
	 * 
	 * @param file
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public static boolean writeStringToFile(File file, String content, String chartset) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			X.string2Stream(content, fos, chartset);
			X.close(fos);
			return true;
		} catch (Exception e) {
			log.error(null, e);
			return false;
		}
	}

	/**
	 * 获取文件大小
	 * 
	 * @param file
	 * @return
	 */
	public static long getFileSize(File file) {
		long rt = -1;
		if (!file.exists()) {
			return -1;
		}
		FileChannel fc = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			fc = fis.getChannel();
			rt = fc.size();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			try {
				if (null != fc) {
					fc.close();
				}
			} catch (IOException io) {
				log.info("io流关闭失败");
			}
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (IOException io) {
				log.info("io流关闭失败");
			}
		}
		return rt;
	}

	/**
	 * copy文件夹下的所有文件到目标文件夹
	 * 
	 * @param src
	 * @param des
	 */
	public static boolean copyFileToFolder(File src, File des, boolean isZip) {
		if (!src.exists()) {
			return false;
		}
		// 遍历该文件夹下的所有文件包括文件夹
		File[] files = src.listFiles();
		for (File file : files) {
			// 如果文件是文件夹，回调本函数
			if (file.isDirectory()) {
				copyFileToFolder(file, des, isZip);
			} else {
				// 执行copy
				File newFile = new File(file.getPath());
				if (isZip) {
					if (newFile.getName().endsWith(".zip")) {
						try {
							FileUtils.copyFileToDirectory(newFile, des);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					if (!newFile.getName().endsWith(".zip")) {
						try {
							FileUtils.copyFileToDirectory(newFile, des);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
		}
		return true;
	}

	/**
	 * 删除非空文件夹
	 * 
	 * @param src
	 * @param des
	 */
	public static boolean deleteFolder(File src) {
		if (!src.exists()) {
			return false;
		}
		// 遍历该文件夹下的所有文件包括文件夹
		File[] files = src.listFiles();
		for (File file : files) {
			// 如果文件是文件夹，回调本函数
			if (file.isDirectory()) {
				deleteFolder(file);
			} else {
				// 执行删除
				file.delete();
			}
		}
		// 删除文件夹
		src.delete();
		return true;
	}
}
