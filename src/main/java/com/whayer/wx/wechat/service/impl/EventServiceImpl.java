package com.whayer.wx.wechat.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whayer.wx.wechat.dao.EventDao;
import com.whayer.wx.wechat.service.EventService;
import com.whayer.wx.wechat.vo.WechatAccount;

@Service
public class EventServiceImpl implements EventService{
	
	@Resource
	private EventDao eventDao;

	/**
	 * 保存用户公众号unionid与openid
	 * @param msgid
	 * @param openid
	 * @param unionid
	 */
	@Override
	public void saveUnionIdAndOpenId(WechatAccount wechatAccount) {
		eventDao.saveUnionIdAndOpenId(wechatAccount);
	}

	/**
	 * 消息排重(幂等)
	 * @param msgid
	 * @return
	 */
	@Override
	public boolean isMsgIdIsExist(String msgid) {
		int result = eventDao.isMsgIdIsExist(msgid);
		
		return result > 0;
	}
	
	@Override
	public boolean isUnionIdIsExist(String unionid) {
		int result = eventDao.isUnionIdIsExist(unionid);
		return result > 0;
	}

	/**
	 * 更新小程序id
	 */
	@Override
	public void updateMiniProgramAppId(String unionid, String miniprogramOpenid) {
		eventDao.updateMiniProgramAppId(unionid, miniprogramOpenid);
	}

	@Override
	public WechatAccount getWechatAccountByUnionId(String unionid) {
		return eventDao.getWechatAccountByUnionId(unionid);
	}

}
