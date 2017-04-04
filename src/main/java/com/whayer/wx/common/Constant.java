package com.whayer.wx.common;

import java.io.File;

/**
 * 全局常量 （如X.UTF8）
 * @author duyu
 * @since 27-02-17
 */
public interface Constant {
	public static final String TIMEA = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMEB = "yy-MM-dd HH:mm:ss";
	public static final String TIMEC = "yyyy.MM.dd";
	public static final String TIMED = "yy-MM-dd";
	public static final String TIMEE = "HH:mm:ss";
	public static final String TIMEF = "yyyyMMddHHmmssSSS";
	public static final String TIMEG = "yyyyMMdd";

	public static final String FILE_SEPARATOR = File.separator;
	
	public static final String  UNDERLINE = "_";
	public static final String  ENCRYPTED = "+";
	public static final String  DECRYPTED = "-";

	public static final String GBK       = "GBK";
	public static final String UTF16LE   = "UTF-16LE";
	public static final String UTF16BE   = "UTF-16BE";
	public static final String UTF8      = "UTF-8";
	public static final String GB2312    = "GB2312";
	
	public static final String  REGEX    = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\s]";
	public static final String  REGEX2   = "[`~!@#$%^*()+=|{}':;',//[//].<>/?~！@#￥%……*（）——+|{}【】‘；：”“’。，、？\\s]";  // 除了斜杠的特殊符号
	public static final String  ZIP_FILE = "zip";
	public static final String  RAR_FILE = "rar";
	
	public static final boolean TRUE     = true;
	public static final boolean FALSE    = false;
	public static final String  SUCCESS  = "success";
	public static final String  FAIL     = "fail";
	
	public static final String  ROOT     = "root";
	public static final String  WEB_ROOT = "/";
	
	public static final String  KEY      = "key";
	public static final String  EMPTY    = "";
	
	//================FOR SK==============================
	public static final String  USER_PASS_PREFIX = "_sk_";
	public static final String  TIME             = "time";
	
	public static final String  GET              = "GET";
	public static final String  POST             = "POST";
	
	public static final String SESSION_ID        = "sessionid";
	public static final String USER              = "user";
	public static final String USER_NAME         = "username";
	public static final String PASSWORD          = "password";
	public static final String USERID            = "userid";
	public static final String USER_TYPE         = "userType";
	  
	/**
	 * 数据库配置, 第三方API地址也可写在此处
	 */
}
