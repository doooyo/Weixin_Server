package com.whayer.wx.common.wechart;

import java.util.Arrays;

import com.whayer.wx.common.encrypt.SHA1;

/**
 * 验证工具类
 * @author duyu	
 * @since  28-02-17
 */
public final class VerifyUtil {
	
	/**
	 * 自定义token
	 */
	public static final String token = "doyo"; 
	
	/**
	 * 验证签名
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean verifySignature(String signature, String timestamp, String nonce){
		String[] arr = {token, timestamp, nonce};
		Arrays.sort(arr);
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			buffer.append(arr[i]);
		}
		
		String sha1Str = SHA1.getSha1(buffer.toString());
		
		return sha1Str.equals(signature);
	}
	
}
