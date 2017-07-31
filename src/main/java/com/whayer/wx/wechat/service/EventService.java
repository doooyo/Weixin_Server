package com.whayer.wx.wechat.service;

import java.util.List;
import java.util.Map;

import com.whayer.wx.wechat.vo.CardInfo;
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
	public void updateMiniProgramOpenId(String unionid, String miniprogramOpenid);
	
	/**
	 * 通过unionId获取所有openId账号
	 * @param unionid
	 * @return
	 */
	public WechatAccount getWechatAccountByUnionId(String unionid);
	
	/**
	 * 通过小程序openId 去尝试获取所有账号id
	 * @param miniProgramAppId
	 * @return
	 */
	public WechatAccount getWechatAccountByMiniProgramOpenId(String miniProgramAppId);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public CardInfo createCardInfo(Map<String, Object> map);
	
	/**
	 * 保存卡劵详情
	 * @param cardInfo
	 * @return
	 */
	public boolean saveCardInfo(CardInfo cardInfo);
	
	/**
	 * 删除卡劵(用于同步更新卡劵时)
	 * @param cardId
	 * @return
	 */
	public boolean deleteCardById(String cardId);
	
	/**
	 * 获取所有可用卡劵列表
	 * @param roleId
	 * @return
	 */
	public List<CardInfo> getCardListDetail(String roleId);
	
	/**
	 * 通过card_id 批量获取详情
	 * @param cardIds
	 * @return
	 */
	public List<CardInfo> getCardListDetailByCardIds(List<String> cardIds);
	
	/**
	 * 查询未在数据库中的卡劵(即需要同步的卡劵id列表)
	 * @param cardIds
	 * @return
	 */
	public List<String> getCardIds();
	
	/**
	 * 批量删除卡劵
	 * @param cardIds
	 * @return
	 */
	public boolean deleteCardByIds(List<String> cardIds);
}
