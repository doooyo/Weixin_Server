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

import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.product.service.ProductCategoryService;
import com.whayer.wx.product.vo.ProductCategory;

@Controller
@RequestMapping("/product2category")
public class ProductCategoryController extends BaseController{
	
	private final static Logger log = LoggerFactory.getLogger(ProductCategoryController.class);
	
	@Resource
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getList(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductCategoryController.getList()");
		Box box = loadNewBox(request);
		
		String name = box.$p("name");
		
		List<ProductCategory> list = productCategoryService.getCategoryList(name);
		
		ResponseCondition res = getResponse(X.TRUE);
		res.setList(list);
		return res;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition saveCategory(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductCategoryController.saveCategory()");
		Box box = loadNewBox(request);
		
		String name = box.$p("name");
		if(isNullOrEmpty(name)){
			return getResponse(X.FALSE);
		}
		
		ProductCategory pc = new ProductCategory();
		pc.setId(X.uuidPure());
		pc.setName(name.trim());
		
		int count = productCategoryService.saveCategory(pc);
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("添加产品分类失败");
			log.error("添加产品分类失败");
			return res;
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition updateCategory(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductCategoryController.updateCategory()");
		Box box = loadNewBox(request);
		
		String id = box.$p("id");
		String name = box.$p("name");
		
		if(isNullOrEmpty(name) || isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		ProductCategory pc = new ProductCategory();
		pc.setId(id);
		pc.setName(name.trim());
		
		int count = productCategoryService.updateCategory(pc);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("更新产品分类失败");
			log.error("更新产品分类失败");
			return res;
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition deleteCategory(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductCategoryController.deleteCategory()");
		Box box = loadNewBox(request);
		
		String id = box.$p("id");
		
		if(isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		int count = productCategoryService.deleteCategoryById(id);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("删除产品分类失败");
			log.error("删除产品分类失败");
			return res;
		}
	}
}
