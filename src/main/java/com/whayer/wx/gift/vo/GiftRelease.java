package com.whayer.wx.gift.vo;

import java.io.Serializable;
import java.util.Date;

public class GiftRelease implements Serializable{

	private static final long serialVersionUID = 4376297574411592302L;
	
	private String id;
	private String giftId;
	private boolean isMailed;
	private String address;
	private String name;
	private String mobile;
	private Date createTime;  
	private Date releaseTime; //发放日期(当确认邮寄时)
	private String orderId;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	public boolean isMailed() {
		return isMailed;
	}
	public void setMailed(boolean isMailed) {
		this.isMailed = isMailed;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "GiftRelease [id=" + id + ", giftId=" + giftId + ", isMailed=" + isMailed + ", address=" + address
				+ ", name=" + name + ", mobile=" + mobile + ", createTime=" + createTime + ", releaseTime="
				+ releaseTime + ", orderId=" + orderId + "]";
	}
}
