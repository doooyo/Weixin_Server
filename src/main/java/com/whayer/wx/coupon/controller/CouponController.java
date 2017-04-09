package com.whayer.wx.coupon.controller;

import java.math.BigDecimal;

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
import com.whayer.wx.coupon.service.CouponService;
import com.whayer.wx.coupon.vo.Coupon;
import com.whayer.wx.login.vo.SkUser;

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
	
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition findById(HttpServletRequest request, HttpServletResponse response){
		log.info("CouponController.findById()");
		Box box = loadNewBox(request);
		String id = box.$p("id");
		if(isNullOrEmpty(id)){
			return getResponse(400, false);
		}
		Coupon coupon = couponService.getCouponById(id);
		ResponseCondition res = getResponse(200, true);
		res.setResult(coupon);
		return res;
	}
	
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition deleteById(HttpServletRequest request, HttpServletResponse response){
		log.info("CouponController.deleteById()");
		Box box = loadNewBox(request);
		
		
		String id = box.$p("id");
		if(null == id){
			return getResponse(400, false);
		}
		
		int count = couponService.deleteCouponById(id);
		if(count > 0) return getResponse(200, true);
		else return getResponse(400, false);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition save(HttpServletRequest request, HttpServletResponse response){
		log.info("CouponController.save()");
		Box box = loadNewBox(request);
		
		String uid = box.$cv(X.USERID);
		uid = isNullOrEmpty(uid) ? ((SkUser)request.getSession().getAttribute(X.USER)).getId() : uid;
		
		String id = X.uuidPure();
		String userId = box.$p("userId");
		String createUserId = uid;
		BigDecimal amount = new BigDecimal(box.$p("amount"));

		if(isNullOrEmpty(userId) || isNullOrEmpty(amount)){
			return getResponse(400, false);
		}
		Coupon coupon = new Coupon();
		coupon.setId(id);
		coupon.setUserId(userId);
		coupon.setCreateUserId(createUserId);
		coupon.setAmount(amount);
		
		
		int count = couponService.saveCoupon(coupon);
		
		if(count > 0) {
			ResponseCondition res = getResponse(200, true);
			res.setResult(coupon);
			return res;
		}
		else return getResponse(500, false);
	}
}