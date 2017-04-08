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
		
		List<Product> list = productService.getProductList();
		
		ResponseCondition res = getResponse(200, true);
		res.setList(list);
		
		return res;
	}
	
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition findById(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductController.findById()");
		Box box = loadNewBox(request);
		
		
		String id = box.$p("id");
		if(null == id){
			return getResponse(400, false);
		}
		Product product = productService.getProductById(id);
		
		ResponseCondition res = getResponse(200, true);
		res.setResult(product);
		
		return res;
	}
	
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition deleteById(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductController.deleteById()");
		Box box = loadNewBox(request);
		
		
		String id = box.$p("id");
		if(null == id){
			return getResponse(400, false);
		}
		
		productService.deleteProductById(id);
		
		ResponseCondition res = getResponse(200, true);
		
		return res;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition save(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductController.save()");
		Box box = loadNewBox(request);
		
		
		String id = UUID.randomUUID().toString();
		String name = box.$p("name");
		String imgUrl = box.$p("imgUrl");
		BigDecimal price = new BigDecimal(box.$p("price"));
		String description = box.$p("description");
		
		if(null == name || null == imgUrl || null == price){
			return getResponse(400, false);
		}
		Product product = new Product();
		product.setId(id);
		product.setImgUrl(imgUrl);
		product.setName(name);
		product.setPrice(price);
		product.setDescription(description);
		
		productService.saveProduct(product);
		
		return getResponse(200, true);
	}
}
