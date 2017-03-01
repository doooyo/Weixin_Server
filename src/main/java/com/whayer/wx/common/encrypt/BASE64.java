package com.whayer.wx.common.encrypt;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * BASE64文本编码解码
 * @author duyu
 * @since  28-02-17
 */
public final class BASE64 {
	private static BASE64Encoder encoder = new BASE64Encoder();
	private static BASE64Decoder decoder = new BASE64Decoder();

	public static String encrypt(String source) {
		return encoder.encode(source.getBytes());
	}

	public static String decrypt(String encrypted) {
		String decrypted = null;
		try {
			decrypted = new String(decoder.decodeBuffer(encrypted));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return decrypted;
	}
}
