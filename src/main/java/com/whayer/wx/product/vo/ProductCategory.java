package com.whayer.wx.product.vo;

import java.io.Serializable;

public class ProductCategory implements Serializable{

	
	private static final long serialVersionUID = 2400283620780381086L;
	
	private String id;   //分类id
	private String name; //商品分类名
	
	public ProductCategory(){
		
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
	@Override
	public String toString() {
		return "ProductCategory [id=" + id + ", name=" + name + "]";
	}
	
}
