package com.whayer.wx.product.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition save(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductController.save()");
		Box box = loadNewBox(request);
		
		ResponseCondition res = new ResponseCondition();
		
		String id = UUID.randomUUID().toString();
		String name = box.$p("name");
		String imgUrl = box.$p("imgUrl");
		BigDecimal price = new BigDecimal(box.$p("price"));
		String description = box.$p("description");
		
		if(null == name || null == imgUrl || null == price){
			res.setHttpCode(400);
			res.setIsSuccess(false);
			return res;
		}
		Product product = new Product();
		product.setId(id);
		product.setImgUrl(imgUrl);
		product.setName(name);
		product.setPrice(price);
		product.setDescription(description);
		
		int result = productService.saveProduct(product);
		if(result > 0){
			res.setHttpCode(200);
			res.setIsSuccess(true);
		}
		return res;
	}
}
