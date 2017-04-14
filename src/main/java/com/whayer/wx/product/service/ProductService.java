package com.whayer.wx.product.service;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.product.vo.Product;

public interface ProductService {
	
	/**
	 * 获取所有产品列表
	 * @return
	 */
	public PageInfo<Product> getProductList(Pagination pagination);
	
	/**
	 * 通过编码获取产品列表
	 * @param Code
	 * @param pagination
	 * @return
	 */
	public PageInfo<Product> getProductListByUserType(String Code, Pagination pagination);
	
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
	
	/**
	 * 角色关联产品
	 * @param role  角色编码
	 * @param ids   产品id数组
	 * @return
	 */
	public Integer associate(String role, String... ids);
	
}
