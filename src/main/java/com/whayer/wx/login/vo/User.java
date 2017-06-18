package com.whayer.wx.login.vo;

import java.io.Serializable;
//import java.sql.Blob;
import java.util.Arrays;


public class User implements Serializable{

	private static final long serialVersionUID = -9040151004036751096L;
	
	private String id;
	private String pId;      //父级代理ID
	private String username; //用户名
	private String password; //用户密码
	private Long points = 0L;//积分
	private byte[] photo;    //头像
	private Boolean isAudit = false; //审核状态    0:未审核   1:已审核
	private Boolean isAgent = false; //是否区域代理 0:个人代理 1:区域代理
	private Integer userType;        //用户类型    0:代理用户 1:集团用户
	private String mobile;      //手机
	private String nickName;    //昵称(主要用于集团用户)
	private String headImg;     //头像地址
	
	private String email;     //邮件
	private String idCardNo;  //身份证ID
	private String bank;      //开户银行
	private String bankCardNo;//银行卡号
	private String idCardImg; //身份证图片
	
	private String bankCardName; //银行卡户名
	private String address;      //收件地址
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getPoints() {
		return points;
	}
	public void setPoints(Long points) {
		this.points = points;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public Boolean getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(Boolean isAudit) {
		this.isAudit = isAudit;
	}
	public Boolean getIsAgent() {
		return isAgent;
	}
	public void setIsAgent(Boolean isAgent) {
		this.isAgent = isAgent;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getIdCardImg() {
		return idCardImg;
	}
	public void setIdCardImg(String idCardImg) {
		this.idCardImg = idCardImg;
	}
	public String getBankCardName() {
		return bankCardName;
	}
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", pId=" + pId + ", username=" + username + ", password=" + password + ", points="
				+ points + ", photo=" + Arrays.toString(photo) + ", isAudit=" + isAudit + ", isAgent=" + isAgent
				+ ", userType=" + userType + ", mobile=" + mobile + ", nickName=" + nickName + ", headImg=" + headImg
				+ ", email=" + email + ", idCardNo=" + idCardNo + ", bank=" + bank + ", bankCardNo=" + bankCardNo
				+ ", idCardImg=" + idCardImg + ", bankCardName=" + bankCardName + ", address=" + address + "]";
	}
}
