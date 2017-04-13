package com.whayer.wx.login.vo;

import java.io.Serializable;

public class Company implements Serializable{
	
	private static final long serialVersionUID = 2435748129035120962L;
	
	private String id;   
	private String name; //集团名
	private String code; //集团编码
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
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", code=" + code + "]";
	}
}
