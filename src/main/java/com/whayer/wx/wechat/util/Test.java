package com.whayer.wx.wechat.util;

import java.util.Arrays;

import com.mohoo.wechat.card.config.BaseConfig;
import com.mohoo.wechat.card.service.WxVipService;
import com.whayer.wx.common.encrypt.SHA1;
import com.whayer.wx.pay.util.RandomUtils;

public class Test {
	
	private static WxVipService wcs = null;
	private static BaseConfig bc = null; 
	
	public static void main(String[] args) {
		
		wcs = new WxVipService();
		bc = new BaseConfig();
		bc.setGetToken(true);
		bc.setSecret(Constant.APPSECRET);
		bc.setAppid(Constant.APPID);
		wcs.setBaseConfig(bc);
		
		//String accessToken = wcs.getAccessToken();
		String apiTicket = wcs.getWxCardTicket();
		String cardId = "pwN5lwDY10uFwYHt1YnDZQH5bVU0";
		String nonceStr = RandomUtils.generateMixString(32);;
		String timestamp = String.valueOf(System.currentTimeMillis()/1000);
		
		String[] arr = {apiTicket, cardId, nonceStr, timestamp};
		Arrays.sort(arr, String.CASE_INSENSITIVE_ORDER);
		
		StringBuilder sb = new StringBuilder();
        for(int i = 0; i < arr.length; i ++) {
            sb.append(arr[i]);
        }
        String result = sb.toString();
        
        String signature = SHA1.getSha1(result);
        System.out.println(
        		"ticket:" +apiTicket 
        		+ "\n cardId:" +cardId 
        		+ "\n nonceStr:" + nonceStr
        		+ "\n timestamp:" + timestamp
        		+ "\n signature:" + signature);
	}
	
}
