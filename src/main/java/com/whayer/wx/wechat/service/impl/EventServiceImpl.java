package com.whayer.wx.wechat.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whayer.wx.common.X;
import com.whayer.wx.wechat.dao.EventDao;
import com.whayer.wx.wechat.service.EventService;
import com.whayer.wx.wechat.util.CARD_TYPE;
import com.whayer.wx.wechat.util.DATE_TYPE;
import com.whayer.wx.wechat.vo.CardInfo;
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
	public void updateMiniProgramOpenId(String unionid, String miniprogramOpenid) {
		eventDao.updateMiniProgramOpenId(unionid, miniprogramOpenid);
	}

	@Override
	public WechatAccount getWechatAccountByUnionId(String unionid) {
		return eventDao.getWechatAccountByUnionId(unionid);
	}

	@Override
	public CardInfo createCardInfo(Map<String, Object> map) {
		JSONObject card = JSONObject.parseObject(JSON.toJSONString(map.get("card")));
		if(null == card) return null;
		
		String card_type = card.getString("card_type");
		CardInfo cardInfo = new CardInfo();
		
		JSONObject cardObj = null;
		JSONObject base_info = null;
		//代金劵
		if(card_type.equalsIgnoreCase(CARD_TYPE.CASH.toString())){
			cardObj = card.getJSONObject(CARD_TYPE.CASH.toString().toLowerCase());
			if(null == cardObj) return null;
			base_info = cardObj.getJSONObject("base_info");
			if(null == base_info) return null;
			
			cardInfo.setCardType(CARD_TYPE.CASH.toString());
			
			cardInfo.setStatus(base_info.getString("status"));
			
			cardInfo.setLeastCost(cardObj.getInteger("least_cost"));
			cardInfo.setReduceCost(cardObj.getInteger("reduce_cost"));
			
				
		}
		//折扣劵
		if(card_type.equalsIgnoreCase(CARD_TYPE.DISCOUNT.toString())){
			cardObj = card.getJSONObject(CARD_TYPE.DISCOUNT.toString().toLowerCase());
			if(null == cardObj) return null;
			base_info = cardObj.getJSONObject("base_info");
			if(null == base_info) return null;
			
			cardInfo.setCardType(CARD_TYPE.DISCOUNT.toString());
			
			cardInfo.setStatus(base_info.getString("status"));
			
			cardInfo.setDiscount(cardObj.getInteger("discount"));
			
		}
		//会员卡
		if(card_type.equalsIgnoreCase(CARD_TYPE.MEMBER_CARD.toString())){
			cardObj = card.getJSONObject(CARD_TYPE.MEMBER_CARD.toString().toLowerCase());
			if(null == cardObj) return null;
			base_info = cardObj.getJSONObject("base_info");
			if(null == base_info) return null;
			
			cardInfo.setCardType(CARD_TYPE.MEMBER_CARD.toString());
			
			cardInfo.setStatus(base_info.getString("status"));
			// TODO 
		}
		
		cardInfo.setId(X.uuidPure());
		//cardInfo.setCardType(CARD_TYPE.CASH.toString());
		cardInfo.setCardId(base_info.getString("id"));
		cardInfo.setTitle(base_info.getString("title"));
		cardInfo.setDescription(base_info.getString("description"));
		
		JSONObject date_info = base_info.getJSONObject("date_info");
		if(null != date_info){
			String dateType = date_info.getString("type");
			cardInfo.setDateType(dateType);
			if(dateType.equalsIgnoreCase(DATE_TYPE.DATE_TYPE_FIX_TERM.toString())){
				cardInfo.setFixedTerm(date_info.getInteger("fixed_term"));
				cardInfo.setFixedBeginTerm(date_info.getInteger("fixed_begin_term"));
			}
			if(dateType.equalsIgnoreCase(DATE_TYPE.DATE_TYPE_FIX_TIME_RANGE.toString())){
				cardInfo.setBeginTimestamp(date_info.getString("begin_timestamp"));
				cardInfo.setEndTimestamp(date_info.getString("end_timestamp"));
			}
		}
		
		return cardInfo;
	}

	@Override
	public boolean saveCardInfo(CardInfo cardInfo) {
		int result = eventDao.saveCardInfo(cardInfo);
		return result > 0;
	}

	@Override
	public List<CardInfo> getCardListDetail(String roleId) {
		return eventDao.getCardListDetail(roleId);
	}

	@Override
	public WechatAccount getWechatAccountByMiniProgramOpenId(String miniProgramOpenId) {
		return eventDao.getWechatAccountByMiniProgramOpenId(miniProgramOpenId);
	}

	@Override
	public List<CardInfo> getCardListDetailByCardIds(List<String> cardIds) {
		
		return eventDao.getCardListDetailByCardIds(cardIds);
	}

	@Override
	public List<String> getCardIds() {
		
		return eventDao.getCardIds();
	}

}
