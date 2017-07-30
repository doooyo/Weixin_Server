package com.whayer.wx.wechat.vo;

import java.util.Date;

/**
 * 同步的卡劵信息
 * @author duyu
 *
 */
public class CardInfo {
	
	private String id;
	private String cardType;
	
	//**********base info***************
	private String cardId;
	private String title;
	private String description;
	private String dateType;      //使用时间类型(请见枚举)
	private String beginTimestamp;//启用时间(dateType为DATE_TYPE_FIX_TIME_RANGE时有效,单位:秒)
	private String endTimestamp;  //结束时间(dateType为DATE_TYPE_FIX_TIME_RANGE时有效,单位:秒)
	private Integer fixedTerm;     //自领取后多少天内有效  (dateType为DATE_TYPE_FIX_TERM时有效,单位:天)
	private Integer fixedBeginTerm;//自领取后多少天开始生效 (dateType为DATE_TYPE_FIX_TERM时有效,单位:天)   
	
	private String status;         //卡劵状态(请见枚举)
	
	//**********differentiation field***************
	private Integer leastCost;     //启用金额(代金劵专用, 单位:分)
	private Integer reduceCost;    //减免金额(代金劵专用, 单位:分)
	
	private Integer discount;      //打折额度(折扣劵专用, 30代表7折)
	
	private Boolean supplyBalance; //会员卡专用,表示是否支持积分，填写true或false，如填写true，积分相关字段均为必填
	private Boolean supplyBonus;   //会员卡专用,表示否支持储值，填写true或false，如填写true，储值相关字段均为必填
	private String bonusCleared;   //会员卡专用,积分清零规则
	private String bonusRules;     //会员卡专用,积分规则
	private String balanceRules;   //会员卡专用,储值规则
	private String prerogative;    //会员卡专用,特权说明
	private String bindOldCardUrl; //会员卡专用,绑定旧卡的url
	private String activateUrl;    //会员卡专用,激活会员卡
	private Boolean needPushOnView;//会员卡专用,进入会员卡时是否推送事件，填写true或false
	
	private Date notifyDate;       //审核通过通知时间 
	
	
	public Date getNotifyDate() {
		return notifyDate;
	}
	public void setNotifyDate(Date notifyDate) {
		this.notifyDate = notifyDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getBeginTimestamp() {
		return beginTimestamp;
	}
	public void setBeginTimestamp(String beginTimestamp) {
		this.beginTimestamp = beginTimestamp;
	}
	public String getEndTimestamp() {
		return endTimestamp;
	}
	public void setEndTimestamp(String endTimestamp) {
		this.endTimestamp = endTimestamp;
	}
	public Integer getFixedTerm() {
		return fixedTerm;
	}
	public void setFixedTerm(Integer fixedTerm) {
		this.fixedTerm = fixedTerm;
	}
	public Integer getFixedBeginTerm() {
		return fixedBeginTerm;
	}
	public void setFixedBeginTerm(Integer fixedBeginTerm) {
		this.fixedBeginTerm = fixedBeginTerm;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getLeastCost() {
		return leastCost;
	}
	public void setLeastCost(Integer leastCost) {
		this.leastCost = leastCost;
	}
	public Integer getReduceCost() {
		return reduceCost;
	}
	public void setReduceCost(Integer reduceCost) {
		this.reduceCost = reduceCost;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Boolean getSupplyBalance() {
		return supplyBalance;
	}
	public void setSupplyBalance(Boolean supplyBalance) {
		this.supplyBalance = supplyBalance;
	}
	public Boolean getSupplyBonus() {
		return supplyBonus;
	}
	public void setSupplyBonus(Boolean supplyBonus) {
		this.supplyBonus = supplyBonus;
	}
	public String getBonusCleared() {
		return bonusCleared;
	}
	public void setBonusCleared(String bonusCleared) {
		this.bonusCleared = bonusCleared;
	}
	public String getBonusRules() {
		return bonusRules;
	}
	public void setBonusRules(String bonusRules) {
		this.bonusRules = bonusRules;
	}
	public String getBalanceRules() {
		return balanceRules;
	}
	public void setBalanceRules(String balanceRules) {
		this.balanceRules = balanceRules;
	}
	public String getPrerogative() {
		return prerogative;
	}
	public void setPrerogative(String prerogative) {
		this.prerogative = prerogative;
	}
	public String getBindOldCardUrl() {
		return bindOldCardUrl;
	}
	public void setBindOldCardUrl(String bindOldCardUrl) {
		this.bindOldCardUrl = bindOldCardUrl;
	}
	public String getActivateUrl() {
		return activateUrl;
	}
	public void setActivateUrl(String activateUrl) {
		this.activateUrl = activateUrl;
	}
	public Boolean getNeedPushOnView() {
		return needPushOnView;
	}
	public void setNeedPushOnView(Boolean needPushOnView) {
		this.needPushOnView = needPushOnView;
	}
	
}
