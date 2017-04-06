package com.whayer.wx.product.service;

import java.util.List;

import com.whayer.wx.product.vo.Product;

public interface ProductService {
	
	/**
	 * 获取产品列表
	 * @return
	 */
	public List<Product> getProductList();
	
	/**
	 * 通过产品ID获取产品详情
	 * @param id
	 * @return
	 */
	public Product getProductById(String id);
	
	/**
	 * 保存产品
	 * @param product
	 * @return
	 */
	public Integer saveProduct(Product product);
	
	/**
	 * 通过ID删除产品
	 * @param id
	 * @return
	 */
	public Integer deleteProductById(String id);
	
}
