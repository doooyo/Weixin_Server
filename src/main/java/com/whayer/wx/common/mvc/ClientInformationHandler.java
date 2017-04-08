package com.whayer.wx.common.mvc;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端信息处理器
 * 抓取http header信息到PageView
 * @author duyu
 * 
 */
public class ClientInformationHandler {
	/**
	 * 加载request header 中附带的浏览器信息,存储到PageView 对象中
	 * 
	 * @param request
	 * @param box
	 */
	public static void load(HttpServletRequest request, PageView pageView) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		pageView.setIp(ip);
		pageView.setReferer(request.getHeader("referer"));
		String agent = request.getHeader("user-agent");
		pageView.setAgent(agent);
		pageView.setOs(getOS(agent));
		pageView.setBrowser(getBrowser(agent));
		pageView.setUrl(request.getRequestURL().toString());
	}

	/**
	 * @param userAgent
	 * @return 客户端操浏览器
	 */
	private static String getBrowser(String userAgent) {
		if (userAgent.indexOf("MSIE") > 0)
			return "IE";
		if (userAgent.indexOf("Firefox") > 0)
			return "Firefox";
		if (userAgent.indexOf("Opera") > 0)
			return "Opera";
		if (userAgent.indexOf("Chrome") > 0)
			return "Chrome";
		if (userAgent.indexOf("Safari") > 0)
			return "Safari";
		if (userAgent.indexOf("MQQBrowser") > 0)
			return "MQQBrowser";
		return "Unknow";
	}

	/**
	 * @param userAgent
	 * @return 客户端操作系统
	 */
	private static String getOS(String userAgent) {
		if (userAgent.indexOf("Windows") > 0)
			return "Windows";
		if (userAgent.indexOf("Android") > 0)
			return "Android";
		if (userAgent.indexOf("Linux") > 0)
			return "Linux";
		if (userAgent.indexOf("iPhone") > 0)
			return "iPhone";
		if (userAgent.indexOf("iOS") > 0)
			return "iOS";
		if (userAgent.indexOf("SymbianOS") > 0)
			return "SymbianOS";
		if (userAgent.indexOf("OS X") > 0)
			return "OSX";
		return "Unknow";
	}
}
