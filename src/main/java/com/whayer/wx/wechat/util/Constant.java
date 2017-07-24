package com.whayer.wx.wechat.util;

import com.whayer.wx.wechat.vo.AccessToken;

public class Constant {

	public static final String APPID = "wx1fa5a2f6ba043f0c"; //"wxe6d4623ad9018e7e";
	public static final String APPSECRET = "ac9a32235b65545cbb08764f5b3a631a"; //"b371c2eb4c348828367ab949ee7d4f39";
	public static final String TOKEN = "scskss";
	
	public static final String ORIGINALID = "gh_af6d9f826816";//minipro
	
	public static final String ACCESS_TOKEN_URL =  "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	
	public static AccessToken ACCESS_TOKEN = null;
}
