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

}
