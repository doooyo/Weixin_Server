package com.whayer.wx.coupon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.coupon.dao.CouponDao;
import com.whayer.wx.coupon.service.CouponService;
import com.whayer.wx.coupon.vo.Coupon;

@Service
public class CouponServiceImpl implements CouponService{
	
	@Resource
	private CouponDao couponDao;

	@Override
	public PageInfo<Coupon> getCouponListByUid(String userId, Pagination pagination) {
		
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Coupon> list = couponDao.getCouponListByUid(userId);
		PageInfo<Coupon> pageInfo = new PageInfo<Coupon>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	@Override
	public Coupon getCouponById(String id) {
		return couponDao.getCouponById(id);
	}

	@Override
	public Integer saveCoupon(Coupon coupon) {
		return couponDao.saveCoupon(coupon);
	}

	@Override
	public Integer deleteCouponById(String id) {
		return couponDao.deleteCoupontById(id);
	}

}
