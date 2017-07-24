package com.whayer.wx.wechat.service;

import com.whayer.wx.wechat.vo.WechatAccount;

public interface EventService {
	
	/**
	 * 保存用户公众号unionid与openid
	 * @param wechatAccount
	 */
	public void saveUnionIdAndOpenId(WechatAccount wechatAccount);
	
	/**
	 * 消息排重(幂等)
	 * @param msgid
	 * @return
	 */
	public boolean isMsgIdIsExist(String msgid);
	
	/**
	 * unionid排重
	 * @param unionid
	 * @return
	 */
	public boolean isUnionIdIsExist(String unionid);
	
	/**
	 * 更新小程序的appid
	 * @param unionid
	 * @param miniprogramOpenid
	 */
	public void updateMiniProgramAppId(String unionid, String miniprogramOpenid);
	
	/**
	 * 
	 * @param unionid
	 * @return
	 */
	public WechatAccount getWechatAccountByUnionId(String unionid);
}
