package com.whayer.wx.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whayer.wx.product.dao.ProductDao;
import com.whayer.wx.product.service.ProductService;
import com.whayer.wx.product.vo.Product;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Resource
	ProductDao productDao;

	@Override
	public List<Product> getProductList() {
		return productDao.getProductList();
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
