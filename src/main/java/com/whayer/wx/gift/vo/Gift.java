package com.whayer.wx.gift.vo;

import java.io.Serializable;
import java.util.Date;

public class Gift implements Serializable{

	private static final long serialVersionUID = 6321978549220335548L;

	private String id;
	private String name;    //赠品名
	private String detail;  //赠品描述
	private Date deadline;  //截止日期
	private String imgSrc;  //图片相对路径
	private Date createTime; //创建日期
	private Boolean isEffect;//是否有效
	
	public Gift(){
		
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
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
	public Boolean getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Boolean isEffect) {
		this.isEffect = isEffect;
	}
	@Override
	public String toString() {
		return "Gift [id=" + id + ", name=" + name + ", detail=" + detail + ", deadline=" + deadline + ", imgSrc="
				+ imgSrc + ", createTime=" + createTime + ", isEffect=" + isEffect + "]";
	}
}
