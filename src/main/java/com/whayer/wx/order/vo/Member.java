package com.whayer.wx.order.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员(体检人)
 * @author duyu
 */
public class Member implements Serializable{

	private static final long serialVersionUID = -7730729262365372992L;
	
	private String id;         //会员id
	private String account;    //会员账号
	private String password;  //会员密码
	private Date createTime;   //创建时间
	
	public Member(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Member [id=" + id + ", account=" + account + ", password=" + password + ", createTime=" + createTime
				+ "]";
	}
	
}
