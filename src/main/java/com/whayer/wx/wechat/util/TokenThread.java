package com.whayer.wx.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TokenThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);

	public void run() {
		while (true) {
			try {
				Constant.ACCESS_TOKEN = WechatUtil.getAccessToken(Constant.APPID, Constant.APPSECRET);
				if (null != Constant.ACCESS_TOKEN) {
					log.info("微信公众号access_token:过期时间:{}, token:{} ", Constant.ACCESS_TOKEN.getExpiresIn(), Constant.ACCESS_TOKEN.getToken());
					Thread.sleep((Constant.ACCESS_TOKEN.getExpiresIn() - 200) * 1000);
				} else {
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					log.error("{}", e1);
				}
				log.error("{}", e);
			}
		}
	}
}

