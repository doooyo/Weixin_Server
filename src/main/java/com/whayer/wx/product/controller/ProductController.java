package com.whayer.wx.product.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.product.service.ProductService;
import com.whayer.wx.product.vo.Product;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController{

	private final static Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Resource
	ProductService productService;
	
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getList(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductController.getList()");
		Box box = loadNewBox(request);
		ResponseCondition res = new ResponseCondition();
		
		List<Product> list = productService.getProductList();
		
		res.setResult(list);
		
		return res;
	}
}
