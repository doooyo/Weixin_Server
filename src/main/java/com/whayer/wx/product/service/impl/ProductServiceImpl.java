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
	public PageInfo<Product> getProductList(String name, Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Product> list =  productDao.getProductList(name);
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

	/**
	 * 删除产品
	 * 需要删除角色-产品中间表数据
	 */
	@Override
	public Integer deleteProductById(String id) {
		int r1 = productDao.deleteAssociation(id);
		int r2 =  productDao.deleteProductById(id);
		return r1 + r2;
	}

	/**
	 * 根据用户类型获取产品列表
	 */
	@Override
	public PageInfo<Product> getProductListByUserType(String code, Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		
		/**
		 * type:  1:个人代理 2:区域代理 xxx:集团用户
		 */
		List<Product> list =  productDao.getProductListByUserType(code);
		PageInfo<Product> pageInfo = new PageInfo<Product>(list, pagination.getNavigationSize());
		return pageInfo;
	}
	
	/**
	 * 角色关联产品(打标)
	 * 注意勾选与反选所需要注意的问题,由于是码表,所以要做差集
	 * @param ids 
	 * @return
	 */
	@Override
	public Integer associate(String role, String[] addIds, String[] delIds) {
		int i = 0, j = 0;
		if(null != addIds && addIds.length > 0)
			i =  productDao.associate(role, addIds);
		if(null != delIds && delIds.length > 0)
			j = productDao.deleteAssociates(role, delIds);
		return i + j;
	}

	@Override
	public PageInfo<Product> getProductList2Role(String Code, Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Product> list =  productDao.getProductList2Role(Code);
		PageInfo<Product> pageInfo = new PageInfo<Product>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	/**
	 * 删除指定产品id与所有角色的关联
	 */
	@Override
	public Integer deleteAssociation(String id) {
		return productDao.deleteAssociation(id);
	}

	@Override
	public Integer updateProduct(Product product) {
		
		return productDao.updateProduct(product);
	}

}
