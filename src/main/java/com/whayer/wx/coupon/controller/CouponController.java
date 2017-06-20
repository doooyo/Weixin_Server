package com.whayer.wx.coupon.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.whayer.wx.coupon.service.CouponService;
import com.whayer.wx.coupon.vo.Coupon;
import com.whayer.wx.login.vo.User;


/**
 * TODO  优惠卷 代金劵 添加截止日期
 * @author duyu
 *
 */
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
		
		//TODO 支持模糊匹配
		
//		if(isNullOrEmpty(userId)){
//			return getResponse(false);
//		}
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
			return getResponse(false);
		}
		Coupon coupon = couponService.getCouponById(id);
		ResponseCondition res = getResponse(true);
		List<Coupon> list = new ArrayList<>();
		list.add(coupon);
		res.setList(list);
		return res;
	}
	
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition deleteById(HttpServletRequest request, HttpServletResponse response){
		log.info("CouponController.deleteById()");
		Box box = loadNewBox(request);
		
		
		String id = box.$p("id");
		if(null == id){
			return getResponse(false);
		}
		
		int count = couponService.deleteCouponById(id);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("删除优惠卷失败");
			log.error("删除优惠卷失败");
			return res;
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition save(HttpServletRequest request, HttpServletResponse response){
		log.info("CouponController.save()");
		Box box = loadNewBox(request);
		
		ResponseCondition res = getResponse(X.FALSE);
		
		String uid = box.$cv(X.USERID);
		if(isNullOrEmpty(uid)){
			User user = (User)request.getSession().getAttribute(X.USER);
			if(isNullOrEmpty(user)){
				res.setErrorMsg("请登陆");
				return res;
			}
			uid = user.getId();
		}
		//uid = isNullOrEmpty(uid) ? ((User)request.getSession().getAttribute(X.USER)).getId() : uid;
		
		String id = X.uuidPure8Bit().toUpperCase();
		String userId = box.$p("userId");
		String deadline = box.$p("deadline");
		String createUserId = uid;
		BigDecimal amount = new BigDecimal(box.$p("amount"));

		if(isNullOrEmpty(userId) || isNullOrEmpty(amount) || isNullOrEmpty(deadline)){
			return getResponse(false);
		}
		Coupon coupon = new Coupon();
		coupon.setId(id);
		coupon.setUserId(userId);
		coupon.setCreateUserId(createUserId);
		coupon.setAmount(amount);
		coupon.setDeadline(X.string2date(deadline + " 23:59:59", X.TIMEA));
		
		int count = couponService.saveCoupon(coupon);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			res.setErrorMsg("保存优惠卷失败");
			log.error("保存优惠卷失败");
			return res;
		}
	}
	
	/**
	 * 验证优惠卷与代金劵的有效性
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition validate(HttpServletRequest request, HttpServletResponse response){
		log.info("CouponController.validate()");
		Box box = loadNewBox(request);
		
		String userId = box.$p("userId");
		String type = box.$p("type");
		String code = box.$p("code");
		if(isNullOrEmpty(code) || isNullOrEmpty(type)){
			return getResponse(false);
		}
		
		ResponseCondition res = couponService.validate(getResponse(true), userId, type, code);
		
		return res;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition update(HttpServletRequest request, HttpServletResponse response){
		log.info("CouponController.update()");
		Box box = loadNewBox(request);
		
		String id = box.$p("id");
		String userId = box.$p("userId");
		String amount = box.$p("amount");
		String deadline = box.$p("deadline");
		if(isNullOrEmpty(id) || isNullOrEmpty(userId) 
				|| isNullOrEmpty(amount) || isNullOrEmpty(deadline)){
			return getResponse(X.FALSE);
		}
		BigDecimal fee = new BigDecimal(amount);
		Date deadTime = X.string2date(deadline + " 23:59:59", X.TIMEA);
		
		Coupon coupon = new Coupon();
		coupon.setId(id);
		coupon.setDeadline(deadTime);
		coupon.setAmount(fee);
		coupon.setUserId(userId);
		
		int count = couponService.updateCoupon(coupon);
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("更新优惠卷失败");
			log.error("更新优惠卷失败");
			return res;
		}
	}
	
}
