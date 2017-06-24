package com.whayer.wx.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.product.vo.ProductCategory;

@Repository
public interface ProductCategoryDao extends DAO{

	public List<ProductCategory> getCategoryList(@Param("name") String name);
	
	public int saveCategory(ProductCategory productCategory);
	
	public int updateCategory(ProductCategory productCategory);
	
	public int deleteCategoryById(@Param("id") String id);
}
