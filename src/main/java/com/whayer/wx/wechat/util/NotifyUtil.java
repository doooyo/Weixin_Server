package com.whayer.wx.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.whayer.wx.common.encrypt.SHA1;

/**
 * 验证工具类
 * @author duyu	
 * @since  28-02-17
 */
public final class NotifyUtil {
	
	public static final String MESSAGE_TEXT = "text"; 
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	
	/**
	 * 验证签名
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean verifySignature(String signature, String timestamp, String nonce){
		String[] arr = {Constant.TOKEN, timestamp, nonce};
		Arrays.sort(arr);
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			buffer.append(arr[i]);
		}
		
		String sha1Str = SHA1.getSha1(buffer.toString());
		
		return sha1Str.equals(signature);
	}
	
	public static Map<String, String> xml2Map(HttpServletRequest request) throws IOException, DocumentException{
		Map<String, String> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		
		InputStream ins = request.getInputStream();
		
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	
}
