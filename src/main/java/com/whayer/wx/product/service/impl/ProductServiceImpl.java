package com.whayer.wx.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.product.dao.ProductDao;
import com.whayer.wx.product.service.ProductService;
import com.whayer.wx.product.vo.Product;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Resource
	ProductDao productDao;

	/**
	 * 获取所有产品列表
	 */
	@Override
	public PageInfo<Product> getProductList(Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Product> list =  productDao.getProductList();
		PageInfo<Product> pageInfo = new PageInfo<Product>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	@Override
	public Product getProductById(String id) {
		return productDao.getProductById(id);
	}

	@Override
	public Integer saveProduct(Product product) {
		return productDao.saveProduct(product);
	}

	@Override
	public Integer deleteProductById(String id) {
		return productDao.deleteProductById(id);
	}

	/**
	 * 根据用户类型获取产品列表
	 */
	@Override
	public PageInfo<Product> getProductListByUserType(String code, Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		
		/**
		 * type:  1:个人代理 2:区域代理 3:集团用户
		 */
		List<Product> list =  productDao.getProductListByUserType(code);
		PageInfo<Product> pageInfo = new PageInfo<Product>(list, pagination.getNavigationSize());
		return pageInfo;
	}

}
