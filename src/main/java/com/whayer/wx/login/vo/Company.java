package com.whayer.wx.login.vo;

import java.io.Serializable;
import java.util.Date;

public class Company implements Serializable{
	
	private static final long serialVersionUID = 2435748129035120962L;
	
	private String id;   
	private String name; //集团名
	private String code; //集团编码
	private String logo; //集团logo
	private Date createTime; //创建时间
	
	//2017-06-14 du
	private Date activeBeginTime;
	private Date activeEndTime;
	private float discount = 1;
	
	public Company(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Date getActiveBeginTime() {
		return activeBeginTime;
	}
	public void setActiveBeginTime(Date activeBeginTime) {
		this.activeBeginTime = activeBeginTime;
	}
	public Date getActiveEndTime() {
		return activeEndTime;
	}
	public void setActiveEndTime(Date activeEndTime) {
		this.activeEndTime = activeEndTime;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", code=" + code + ", logo=" + logo + ", createTime="
				+ createTime + ", activeBeginTime=" + activeBeginTime + ", activeEndTime=" + activeEndTime
				+ ", discount=" + discount + "]";
	}
	
}
