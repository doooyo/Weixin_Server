package com.whayer.wx.coupon.controller;

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
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.coupon.service.CouponService;
import com.whayer.wx.coupon.vo.Coupon;

@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController{
	private final static Logger log = LoggerFactory.getLogger(CouponController.class);
	
	@Resource 
	CouponService couponService;
	
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getList(HttpServletRequest request, HttpServletResponse response){
		log.info("CouponController.getList()");
		Box box = loadNewBox(request);
		String userId = box.$p("userId");
		if(isNullOrEmpty(userId)){
			return getResponse(400, false);
		}
		PageInfo<Coupon> pi = couponService.getCouponListByUid(userId, box.getPagination());
		
		return pagerResponse(pi);
	}
}
