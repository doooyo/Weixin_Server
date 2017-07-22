package com.whayer.wx.login.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增集团时需要新增角色
 * @author duyu
 * @since  12-04-17
 */
public class Role implements Serializable{

	private static final long serialVersionUID = 2182354355015210938L;

	private String id;   
	private String code;  //角色编码(1:个人代理 2:区域代理 xx:集团)
	private String name;  //角色名 
	private Date createTime; //创建时间
	
	public Role(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	@Override
	public String toString() {
		return "Role [id=" + id + ", code=" + code + ", name=" + name + ", createTime=" + createTime + "]";
	}
}
