package com.whayer.wx.vouchers.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.vouchers.vo.Voucher;

@Repository
public interface VoucherDao extends DAO{

	public List<Voucher> getVoucherListByUid(@Param("userId") String userId);
	
	public Voucher getVoucherById(@Param("id") String id);
	
	public Integer saveVoucher(Voucher voucher);
	
	public Integer deleteVoucherById(@Param("id") String id);
}
