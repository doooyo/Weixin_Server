package com.whayer.wx.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whayer.wx.product.dao.ProductCategoryDao;
import com.whayer.wx.product.service.ProductCategoryService;
import com.whayer.wx.product.vo.ProductCategory;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Resource
	private ProductCategoryDao productCategoryDao;
	
	@Override
	public List<ProductCategory> getCategoryList(String name) {
		return productCategoryDao.getCategoryList(name);
	}

	@Override
	public int updateCategory(ProductCategory productCategory) {
		return productCategoryDao.updateCategory(productCategory);
	}

	@Override
	public int deleteCategoryById(String id) {
		return productCategoryDao.deleteCategoryById(id);
	}

	@Override
	public int saveCategory(ProductCategory productCategory) {
		return productCategoryDao.saveCategory(productCategory);
	}

}
