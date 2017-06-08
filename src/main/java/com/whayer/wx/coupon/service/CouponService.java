package com.whayer.wx.coupon.service;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.coupon.vo.Coupon;

public interface CouponService {
	/**
	 * 通过用户获取优惠卷列表
	 * @return
	 */
	public PageInfo<Coupon> getCouponListByUid(String userId, Pagination pagination);
	
	/**
	 * 通过优惠卷ID获取优惠卷详情
	 * @param id
	 * @return
	 */
	public Coupon getCouponById(String id);
	
	/**
	 * 添加保存优惠卷
	 * @param product
	 * @return
	 */
	public Integer saveCoupon(Coupon coupon);
	
	/**
	 * 更新优惠卷
	 * @param coupon
	 * @return
	 */
	public Integer updateCoupon(Coupon coupon);
	
	/**
	 * 通过ID删除优惠卷
	 * @param id
	 * @return
	 */
	public Integer deleteCouponById(String id);
	
	/**
	 * 
	 * @param userId  所属用户
	 * @param type    0:优惠卷 1:代金劵
	 * @param code    劵编码
	 * @return
	 */
	public ResponseCondition validate(ResponseCondition res, String userId, String type, String code);
}
