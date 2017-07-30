package com.whayer.wx.wechat.util;

import com.whayer.wx.wechat.vo.AccessToken;

public class Constant {

	public static final String APPID = ""; //"wxe6d4623ad9018e7e";
	public static final String APPSECRET = ""; //"b371c2eb4c348828367ab949ee7d4f39";
	public static final String TOKEN = "";
	
	public static final String ORIGINALID = "";//minipro
	
	public static final String ACCESS_TOKEN_URL =  "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	
	public static AccessToken ACCESS_TOKEN = null;
}
