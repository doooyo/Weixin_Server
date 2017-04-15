package com.whayer.wx.coupon.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.coupon.vo.Coupon;

@Repository
public interface CouponDao extends DAO {
	
	public List<Coupon> getCouponListByUid(@Param("userId") String userId);
	
	public Coupon getCouponById(@Param("id") String id);
	
	public int saveCoupon(Coupon coupon);
	
	public int deleteCoupontById(@Param("id") String id);
	
	public Coupon validate(@Param("userId") String userId, @Param("code") String code);
	
	public int updateStateByIds(@Param("ids") String... ids);
	
	public int updateStateRollBackById(@Param("ids") String... ids);
}
