package com.whayer.wx.product.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.whayer.wx.base.UnitTestBase;
import com.whayer.wx.product.vo.Product;

@RunWith(BlockJUnit4ClassRunner.class)
public class ProductServiceTest extends UnitTestBase{

	public ProductServiceTest() {
		super("classpath:IOC.xml");
	}
	
	@Test
	public void testProductService() {
		ProductService productService = super.getBean("productServiceImpl");
		
		Product product = new Product();
		product.setId(UUID.randomUUID().toString());
		product.setImgUrl("/user/xxx/4.jpg");
		product.setName("产品D");
		product.setPrice(new BigDecimal(30.52));
		product.setDescription("产品D的描述......");
		
		int id = productService.saveProduct(product);
		System.out.println(id);
	}

}
