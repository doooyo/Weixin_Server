package com.whayer.wx.order.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检人
 * @author duyu
 *
 */
public class Examinee implements Serializable{

	private static final long serialVersionUID = 9184952206370359745L;
	
	private String id;      //体检人id
	private String name;    //体检人姓名
	private Integer age;    //体检人年龄
	private String address; //体检人地址
	private Boolean gender; //性别(0: 男1:女)
	private String mobile;  //体检人电话
	private String identityId; //身份证ID
	private Date birthday;     //出生日期
	
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
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
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "Examinee [id=" + id + ", name=" + name + ", age=" + age + ", address=" + address + ", gender=" + gender
				+ ", mobile=" + mobile + ", identityId=" + identityId + ", birthday=" + birthday + "]";
	}
}
