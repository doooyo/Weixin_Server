package com.whayer.wx.coupon.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Coupon implements Serializable{

	private static final long serialVersionUID = -8493923707627500819L;
	
	private String id;
	private String userId;         //发放者id
	private BigDecimal amount;     //金额
	private boolean effectiveness; //是否有效
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public boolean isEffectiveness() {
		return effectiveness;
	}
	public void setEffectiveness(boolean effectiveness) {
		this.effectiveness = effectiveness;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
