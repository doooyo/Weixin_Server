package com.whayer.wx.product.vo;

import java.io.Serializable;

public class Product2Role implements Serializable {

	private static final long serialVersionUID = -606518019946090455L;
	
	private String id;
	private String productId;   //产品id
	private String userCode;    //用户类型编码
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Product2Role [id=" + id + ", productId=" + productId + ", userCode=" + userCode + "]";
	}
}
