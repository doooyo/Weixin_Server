package com.whayer.wx.common.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES文本加密解密
 * @author duyu
 * @since  28-02-17
 */
public final class AES {
	private final static String ERROR = "error";
	static final String KEY_ALGORITHM = "AES";
	private SecretKeySpec key; // 加密密钥
	private Cipher cipher;     // 加密算法
	private final static Logger log = LoggerFactory.getLogger(AES.class);

	/**
	 * 传入密钥
	 * 
	 * @param password
	 */
	public AES(String password) {
		log.trace("Creating an AES instance by password " + password);
		// 获取指定密码的byte
		byte[] p = password.getBytes();
		// 获取原始密码长度
		int s = p.length;
		// 创建长度为16的标准秘钥
		byte[] pwd = new byte[16];
		for (int x = 0, i = 0; x < 16; x++, i += 7) {
			// 循环16次用指定的密码byte 生成标准秘钥
			if (i > s) {
				pwd[x] = p[i % s];
			} else {
				pwd[x] = p[i];
			}
		}
		try {
			// 使用KeyGenerator生成（对称）密钥。
			key = new SecretKeySpec(pwd, "AES");
			cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.toString();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 加密，使用指定数据源生成密钥，使用用户数据作为算法参数进行AES加密
	 * 
	 * @param msg
	 *            加密的数据
	 * @return
	 */
	public String encrypt(String msg) {
		if (msg == null) {
			return ERROR;
		}
		log.debug("Encrypting to aes : " + msg);
		try {
			// 加密并转换成16进制字符串
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			return asHex(cipher.doFinal(msg.getBytes("utf-8")));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 解密，对生成的16进制的字符串进行解密
	 * 
	 * @param value
	 *            解密的数据
	 * @return
	 */
	public String decrypt(String value) {
		if (value == null) {
			return ERROR;
		}
		log.debug("Decrypting from aes : " + value);
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(asBin(value)));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 将字节数组转换成16进制字符串
	 * 
	 * @param buf
	 * @return
	 */
	public String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;
		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}

	/**
	 * 将16进制字符串转换成字节数组
	 * 
	 * @param src
	 * @return
	 */
	public byte[] asBin(String src) {
		if (src.length() < 1)
			return null;
		byte[] encrypted = new byte[src.length() / 2];
		for (int i = 0; i < src.length() / 2; i++) {
			int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);
			encrypted[i] = (byte) (high * 16 + low);
		}
		return encrypted;
	}
}
