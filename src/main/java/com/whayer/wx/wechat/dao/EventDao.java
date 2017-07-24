package com.whayer.wx.wechat.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.wechat.vo.WechatAccount;

@Repository
public interface EventDao extends DAO{
	
	public void saveUnionIdAndOpenId(WechatAccount wechatAccount);
	
	public Integer isMsgIdIsExist(@Param("msgid") String msgid);
	
	public Integer isUnionIdIsExist(@Param("unionid") String unionid);
	
	public Integer updateMiniProgramAppId(
			@Param("unionid") String unionid, 
			@Param("miniprogramOpenid") String miniprogramOpenid);
	
	public WechatAccount getWechatAccountByUnionId(@Param("unionid") String unionid);
}
