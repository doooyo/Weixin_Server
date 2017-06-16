package com.whayer.wx.product.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.product.service.ProductService;

/**
 * 产品打标
 * @author duyu
 *
 */
@Controller
@RequestMapping("/product2role")
public class Product2RoleController extends BaseController{
	
	@Resource
	private ProductService productService;
	
	private final static Logger log = LoggerFactory.getLogger(Product2RoleController.class);
	
	/**
	 * 角色关联产品
	 * @param ids      产品id数组
	 * @param role     角色编码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/associate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition associate(
			@RequestParam(value = "addIds[]", required = false) String[] addIds, 
			@RequestParam(value = "delIds[]", required = false) String[] delIds, 
			@RequestParam("role") String role, 
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Product2RoleController.associate()");
		
		//Box box = loadNewBox(request);
		
		//String role = box.$p("role");
		
		//不能同时为空
		if(isNullOrEmpty(addIds) && isNullOrEmpty(delIds)){
			return getResponse(X.FALSE);
		}
		
		//插入中间表
		int count = productService.associate(role, addIds, delIds);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("产品关联角色失败");
			log.error("产品关联角色失败");
			return res;
		}
	}

}
