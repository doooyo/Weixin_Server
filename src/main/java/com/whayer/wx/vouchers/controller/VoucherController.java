package com.whayer.wx.vouchers.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.login.vo.User;
import com.whayer.wx.vouchers.service.VoucherService;
import com.whayer.wx.vouchers.vo.Voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/voucher")
public class VoucherController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(VoucherController.class);
	
	@Resource
	private VoucherService voucherService;
	
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getList(HttpServletRequest request, HttpServletResponse response){
		log.info("VoucherController.getList()");
		Box box = loadNewBox(request);
		String userId = box.$p("userId");
		if(isNullOrEmpty(userId)){
			return getResponse(false);
		}
		PageInfo<Voucher> pi = voucherService.getVoucherListByUid(userId, box.getPagination());
		
		return pagerResponse(pi);
	}
	
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition findById(HttpServletRequest request, HttpServletResponse response){
		log.info("VoucherController.findById()");
		Box box = loadNewBox(request);
		String id = box.$p("id");
		if(isNullOrEmpty(id)){
			return getResponse(false);
		}
		Voucher voucher = voucherService.getVoucherById(id);
		ResponseCondition res = getResponse(true);
		List<Voucher> list = new ArrayList<>();
		list.add(voucher);
		res.setList(list);
		return res;
	}
	
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition deleteById(HttpServletRequest request, HttpServletResponse response){
		log.info("VoucherController.deleteById()");
		Box box = loadNewBox(request);
		
		
		String id = box.$p("id");
		if(null == id){
			return getResponse(false);
		}
		
		int count = voucherService.deleteVoucherById(id);
		
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
		log.info("VoucherController.save()");
		Box box = loadNewBox(request);
		
		String uid = box.$cv(X.USERID);
		uid = isNullOrEmpty(uid) ? ((User)request.getSession().getAttribute(X.USER)).getId() : uid;
		
		String id = X.uuidPure();
		String userId = box.$p("userId");
		String deadline = box.$p("deadline");
		String createUserId = uid;
		BigDecimal amount = new BigDecimal(box.$p("amount"));

		if(isNullOrEmpty(userId) || isNullOrEmpty(amount) || isNullOrEmpty(deadline)){
			return getResponse(false);
		}
		Voucher voucher = new Voucher();
		voucher.setId(id);
		voucher.setUserId(userId);
		voucher.setCreateUserId(createUserId);
		voucher.setAmount(amount);
		voucher.setDeadline(X.string2date(deadline, X.TIMEA));
		
		int count = voucherService.saveVoucher(voucher);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("保存优惠卷失败");
			log.error("保存优惠卷失败");
			return res;
		}
	}
}
