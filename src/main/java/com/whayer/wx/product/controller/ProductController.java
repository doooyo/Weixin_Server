package com.whayer.wx.product.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.X;
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
	
	/**
	 * 获取所有产品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getList(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductController.getList()");
		Box box = loadNewBox(request);
		
		PageInfo<Product> pi = productService.getProductList(box.getPagination());
		
		return pagerResponse(pi);
	}
	
	/**
	 * 根据角色(用户编码)获取具有权限的产品列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getListByType", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getListByType(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductController.getListByType()");
		Box box = loadNewBox(request);
		
		/**
		 * code: 用户类型
		 * 1:  个人代理编码
		 * 2:  区域代理编码
		 * xxx:集团用户编码
		 */
		
		String code = box.$p("code");
		if(isNullOrEmpty(code)){
			return getResponse(X.FALSE);
		}
		
		PageInfo<Product> pi = productService.getProductListByUserType(code, box.getPagination());
		
		return pagerResponse(pi);
	}
	
	/**
	 * 获取所有产品,并指明和当前角色是否有关联(标识哪些产品是以做关联)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getList2Role", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getList2Role(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductController.getList2Role()");
		Box box = loadNewBox(request);
		
		/**
		 * code: 用户类型
		 * 1:  个人代理编码
		 * 2:  区域代理编码
		 * xxx:集团用户编码
		 */
		
		String code = box.$p("code");
		if(isNullOrEmpty(code)){
			return getResponse(X.FALSE);
		}
		
		PageInfo<Product> pi = productService.getProductList2Role(code, box.getPagination());
		
		return pagerResponse(pi);
	}
	
	/**
	 * 获取产品详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition findById(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductController.findById()");
		Box box = loadNewBox(request);
		
		
		String id = box.$p("id");
		if(null == id){
			return getResponse(false);
		}
		Product product = productService.getProductById(id);
		
		ResponseCondition res = getResponse(true);
		List<Product> list = new ArrayList<>();
		list.add(product);
		res.setList(list);
		return res;
	}
	
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition deleteById(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductController.deleteById()");
		Box box = loadNewBox(request);
		
		
		String id = box.$p("id");
		if(null == id){
			return getResponse(false);
		}
		
		productService.deleteProductById(id);
		
		return getResponse(true);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition save(HttpServletRequest request, HttpServletResponse response){
		log.info("ProductController.save()");
		Box box = loadNewBox(request);
		
		
		String id = X.uuidPure();
		String name = box.$p("name");
		String imgUrl = box.$p("imgUrl");
		BigDecimal price = new BigDecimal(box.$p("price"));
		String description = box.$p("description");
		
		if(null == name || null == imgUrl || null == price){
			return getResponse(false);
		}
		Product product = new Product();
		product.setId(id);
		product.setImgUrl(imgUrl);
		product.setName(name);
		product.setPrice(price);
		product.setDescription(description);
		
		productService.saveProduct(product);
		return getResponse(true);
	}
}
