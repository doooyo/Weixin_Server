package com.whayer.wx.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.product.vo.Product;

@Repository
public interface ProductDao  extends DAO{
	
	public List<Product> getProductList();
	
	public Product getProductById(@Param("id") String id);
	
	public Integer saveProduct(Product product);
	
	public Integer deleteProductById(@Param("id") String id);
	
	public List<Product> getProductListByUserType(@Param("code") String code);
	
	public Integer associate(@Param("role") String role, @Param("ids") String... ids);
	
}
