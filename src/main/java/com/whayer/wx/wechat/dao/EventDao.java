package com.whayer.wx.wechat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.wechat.vo.CardInfo;
import com.whayer.wx.wechat.vo.WechatAccount;

@Repository
public interface EventDao extends DAO{
	
	public void saveUnionIdAndOpenId(WechatAccount wechatAccount);
	
	public Integer isMsgIdIsExist(@Param("msgid") String msgid);
	
	public Integer isUnionIdIsExist(@Param("unionid") String unionid);
	
	public Integer updateMiniProgramOpenId(
			@Param("unionid") String unionid, 
			@Param("miniprogramOpenid") String miniprogramOpenid);
	
	public WechatAccount getWechatAccountByUnionId(@Param("unionid") String unionid);
	
	public WechatAccount getWechatAccountByMiniProgramOpenId(@Param("miniProgramOpenId") String miniProgramOpenId);
	
	public Integer saveCardInfo(CardInfo cardInfo);
	
	public List<CardInfo> getCardListDetail(@Param("role") String role);
	
	public List<CardInfo> getCardListDetailByCardIds(@Param("cardIds")  List<String> cardIds);
	
	public List<String> getCardIds();
	
	public Integer deleteCardById(@Param("cardId") String cardId);
	
	public Integer deleteCardByIds(@Param("cardIds") List<String> cardIds);
}
