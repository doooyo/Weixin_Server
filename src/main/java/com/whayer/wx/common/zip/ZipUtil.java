package com.whayer.wx.common.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whayer.wx.common.X;
import com.whayer.wx.common.io.FileUtil;


/**
 * Zip 压缩解压工具类, 可以压缩目录/文件 解压.zip 文件
 * @author duyu
 * @since  28-02-17
 */
public class ZipUtil {
	private final static Logger log = LoggerFactory.getLogger(ZipUtil.class);

	/**
	 * 将指定的zip文件 压缩到指定的目录,忽略所有zip内部的子目录, 将所有文件 按照 目录1_目录2_文件.txt
	 * 的对应方式平铺在destinationFilePath 所指定的目录下,(其下再无其他子目录). 该类用于将用户上传的不标准的压缩包
	 * 按照统一标准来解压. 便于后续处理.
	 * 
	 * @param sourceFilePath
	 *            (/Users/duyu/Desktop/1.zip)
	 * @param destinationFilePath
	 *            (/Users/duyu/Desktop/)
	 * @param charset
	 *            "UTF-8" "GBK"
	 * @throws Exception
	 */
	public static void unzipToSingleFolder(String sourceFilePath, String destinationFilePath, String charset)
			throws Exception {
		long startTime = System.currentTimeMillis();
		FileOutputStream fos = null;
		InputStream is = null;
		ZipFile zipFile = null;
		File outDirFile = null;
		// 创建输出文件夹对象
		if (destinationFilePath == null) {
			// 未指定输出路径,默认用源zip文件名作为输出目录
			outDirFile = new File(sourceFilePath.substring(0, sourceFilePath.length() - 4));
		} else {
			outDirFile = new File(destinationFilePath);
		}

		if (!outDirFile.isDirectory())
			outDirFile.mkdirs();
		File sourceFile = new File(sourceFilePath);
		String extension = FileUtil.getExtension(sourceFilePath);
		if (!extension.equalsIgnoreCase(X.ZIP_FILE)) {
			throw new Exception("sourceFile is not a zip");
		}
		if (!sourceFile.exists()) {
			throw new Exception("sourceFile not found!");
		}
		try {
			zipFile = new ZipFile(sourceFilePath, Charset.forName(charset));
			for (Enumeration entries = zipFile.entries(); entries.hasMoreElements();) {
				ZipEntry ze = (ZipEntry) entries.nextElement();
				String fileName = ze.getName().replaceAll("\\\\", "_").replaceAll("/", "_");
				File file = new File(outDirFile, fileName);
				if (ze.isDirectory()) {
					// 是目录，则忽略之
				} else {
					file.createNewFile();
					fos = new FileOutputStream(file);
					is = zipFile.getInputStream(ze);
					X.copyStreamAndClose(is, fos);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != zipFile) {
				zipFile.close();
			}
		}
		long endTime = System.currentTimeMillis();
		log.debug("@@Zip decompression : {}  -----------------  duration ： {} ms", sourceFilePath, endTime - startTime);
	}

	/**
	 * 将指定的zip文件 压缩到指定的目录,按照zip中的目录结构原样输出
	 * 
	 * @param sourceFilePath
	 *            (/Users/duyu/Desktop/1.zip)
	 * @param destinationFilePath
	 *            (/Users/duyu/Desktop/)
	 * @param charset
	 *            "UTF-8" "GBK"
	 * @throws Exception
	 */
	public static void unzip(String sourceFilePath, String destinationFilePath, String charset) throws Exception {
		long startTime = System.currentTimeMillis();
		FileOutputStream fos = null;
		InputStream is = null;
		ZipFile zipFile = null;
		// 创建输出文件夹对象
		File outDirFile = new File(destinationFilePath);
		outDirFile.mkdirs();
		File sourceFile = new File(sourceFilePath);
		if (!sourceFilePath.endsWith(".zip")) {
			throw new Exception("sourceFile is not a zip");
		}
		if (!sourceFile.exists()) {
			throw new Exception("sourceFile not found!");
		}
		try {
			zipFile = new ZipFile(sourceFilePath, Charset.forName(charset));
			for (Enumeration entries = zipFile.entries(); entries.hasMoreElements();) {
				ZipEntry ze = (ZipEntry) entries.nextElement();
				File file = new File(outDirFile, ze.getName());
				if (ze.isDirectory()) {// 是目录，则创建之
					file.mkdirs();
				} else {
					File parent = file.getParentFile();
					if (parent != null && !parent.exists()) {
						parent.mkdirs();
					}
					file.createNewFile();
					fos = new FileOutputStream(file);
					is = zipFile.getInputStream(ze);
					X.copyStreamAndClose(is, fos);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			zipFile.close();
		}
		long endTime = System.currentTimeMillis();
		log.debug("@@Zip decompression : {}  -----------------  duration ： {} ms", sourceFilePath, endTime - startTime);
	}

	/**
	 * 将指定的文件或目录 压缩成指定的zip文件
	 * 
	 * @param sourceFilePath
	 *            (/Users/duyu/Desktop/X98) 文件或目录都可以
	 * @param destinationFilePath
	 *            (/Users/duyu/Desktop/1/1.zip)
	 * @param charset
	 *            "UTF-8" "GBK" !!!由于大多数用户用的windows电脑 zip在windows 下会使用GBK编码
	 *            因此这里最好设置成 GBK编码 否则 windows用户用系统自带的zip工具解压将丢失数据.
	 *            但是设成GBK对mac用户不友好,mac自带的解压遵从UTF-8 linux也是 , 但是可以借助第三方压缩工具来解压.
	 *            考虑windows:mac 比例非常高 所以推荐用GBK. 另外,如果待压缩的文件的文件名只有 ASCII字符
	 *            那就无论如何都不会有问题了.. 因此建议对待压缩的文件进行重命名!!
	 * @throws Exception
	 */
	public static void zip(String sourceFilePath, String destinationFilePath, String charset) {
		long startTime = System.currentTimeMillis();
		File sourceFile = new File(sourceFilePath);
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(destinationFilePath), Charset.forName(charset));// 默认编码方式是UTF-8
			compress(sourceFile, sourceFile.getName(), out);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			long endTime = System.currentTimeMillis();
			log.debug("@@Zip compression : {}  -----------------  duration ： {} ms", destinationFilePath,
					endTime - startTime);
		}
	}

	/**
	 * 将指定的文件或目录 压缩成指定的zip文件,并重命名根文件夹
	 * 
	 * @param sourceFilePath
	 *            (/Users/duyu/Desktop/X98) 文件或目录都可以
	 * @param destinationFilePath
	 *            (/Users/duyu/Desktop/1/1.zip)
	 * @param charset
	 *            "UTF-8" "GBK" !!!由于大多数用户用的windows电脑 zip在windows 下会使用GBK编码
	 *            因此这里最好设置成 GBK编码 否则 windows用户用系统自带的zip工具解压将丢失数据.
	 *            但是设成GBK对mac用户不友好,mac自带的解压遵从UTF-8 linux也是 , 但是可以借助第三方压缩工具来解压.
	 *            考虑windows:mac 比例非常高 所以推荐用GBK. 另外,如果待压缩的文件的文件名只有 ASCII字符
	 *            那就无论如何都不会有问题了.. 因此建议对待压缩的文件进行重命名!!
	 * 
	 * @param renameMap
	 *            <String,String>
	 * @throws Exception
	 */
	public static void zipAndRename(String sourceFilePath, String destinationFilePath, String charset,
			String folderName) {
		long startTime = System.currentTimeMillis();
		File sourceFile = new File(sourceFilePath);
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(destinationFilePath), Charset.forName(charset));// 默认编码方式是UTF-8
			compress(sourceFile, folderName, out);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			long endTime = System.currentTimeMillis();
			log.debug("@@Zip compression : {}  -----------------  duration ： {} ms", destinationFilePath,
					endTime - startTime);
		}
	}

	/**
	 * 递归将源目录下的文件全部写入 zip文件中.
	 * 
	 * @param sourceFile
	 *            源文件File
	 * @param sourceFileName
	 *            源文件 文件名
	 * @param out
	 * @throws Exception
	 */
	private static void compress(File sourceFile, String sourceFileName, ZipOutputStream out) throws Exception { // 方法重载
		if (sourceFile.isDirectory()) {
			// 源文件是一个目录
			// 获取该目录下所有文件
			File[] fl = sourceFile.listFiles();
			if (fl.length == 0) {
				// 目录是空目录, 只创建zip中空目录
				out.putNextEntry(new ZipEntry(sourceFileName + "/")); // 创建zip压缩进入点base
			}
			for (int i = 0; i < fl.length; i++) {
				// 遍历目录下所有文件/目录 进行递归操作
				compress(fl[i], sourceFileName + "/" + fl[i].getName(), out); // 递归遍历子文件夹
			}
		} else {
			// 源文件是一个文件
			out.putNextEntry(new ZipEntry(sourceFileName)); // 创建zip压缩进入点base
			FileInputStream fis = new FileInputStream(sourceFile);
			// 将源文件内容写入zip文件
			X.copyStream(fis, out);
			// 关闭源文件流
			X.close(fis);
		}
	}
}
