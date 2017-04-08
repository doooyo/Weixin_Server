package com.whayer.wx.coupon.service;

import org.junit.Test;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.base.UnitTestBase;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.coupon.vo.Coupon;

public class CouponServiceTest extends UnitTestBase{
	
	public CouponServiceTest() {
		super("classpath:IOC.xml");
	}
	
	@Test
	public void testGetCouponList() {
		CouponService couponService = super.getBean("couponServiceImpl");
		PageInfo<Coupon> pi = couponService.getCouponListByUid("00010102", new Pagination());
		ResponseCondition res = new ResponseCondition();
		res.setHttpCode(200);
		res.setIsSuccess(true);
		res.setList(pi.getList());
		res.setPageIndex(pi.getPageNum());
		res.setPageSize(pi.getPageSize());
		res.setTotal(pi.getTotal());
		res.setPages(pi.getPages());
		System.out.println(res.toString());
	}

}
