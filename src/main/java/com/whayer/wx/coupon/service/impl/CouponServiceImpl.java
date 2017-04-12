package com.whayer.wx.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.coupon.dao.CouponDao;
import com.whayer.wx.coupon.service.CouponService;
import com.whayer.wx.coupon.vo.Coupon;
import com.whayer.wx.vouchers.dao.VoucherDao;
import com.whayer.wx.vouchers.vo.Voucher;

@Service
public class CouponServiceImpl implements CouponService{
	
	@Resource
	private CouponDao couponDao;
	
	@Resource
	private VoucherDao voucherDao;

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

	@Override
	public ResponseCondition validate(ResponseCondition res, String userId, String type, String code) {
		/**
		 * 用户Id为空,代表为散客户
		 * 用户Id不为空,代表为集团用户 or 代理人(个代/区代)
		 * type  0:优惠卷 1:代金劵
		 */
		if("0".equals(type)){
			Coupon coupon = couponDao.validate(userId, code);
			if(null == coupon){
				res.setIsSuccess(false);
				res.setErrorMsg("不存在此优惠劵");
				return res;
			}else{
				if(!coupon.isEffect()){
					res.setIsSuccess(false);
					res.setErrorMsg("优惠劵已使用");
					return res;
				}else if(coupon.isExpired()){
					res.setIsSuccess(false);
					res.setErrorMsg("优惠劵已过期");
					return res;
				}else{
					List<Coupon> list = new ArrayList<>();
					list.add(coupon);
					res.setList(list);
					return res;
				}
			}
			
		}else{
			Voucher voucher = voucherDao.validate(userId, code);
			if(null == voucher){
				res.setIsSuccess(false);
				res.setErrorMsg("不存在此代金劵");
				return res;
			}else{
				if(!voucher.isEffect()){
					res.setIsSuccess(false);
					res.setErrorMsg("代金劵已使用");
					return res;
				}else if(voucher.isExpired()){
					res.setIsSuccess(false);
					res.setErrorMsg("代金劵已过期");
					return res;
				}else{
					List<Voucher> list = new ArrayList<>();
					list.add(voucher);
					res.setList(list);
					return res;
				}
			}
		}
		
	}

}
