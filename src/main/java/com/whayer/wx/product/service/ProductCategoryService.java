package com.whayer.wx.product.service;

import java.util.List;

import com.whayer.wx.product.vo.ProductCategory;

public interface ProductCategoryService {
	
	/**
	 * 获取产品分类列表
	 * @param name 分类名模糊搜索
	 * @return
	 */
	public List<ProductCategory> getCategoryList(String name);
	
	/**
	 * 添加产品分类
	 * @param productCategory
	 * @return
	 */
	public int saveCategory(ProductCategory productCategory);
	
	/**
	 * 更新产品分类
	 * @param id
	 * @return
	 */
	public int updateCategory(ProductCategory productCategory);
	
	/**
	 * 删除产品分类
	 * @param id
	 * @return
	 */
	public int deleteCategoryById(String id);
}
