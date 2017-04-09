package com.whayer.wx.vouchers.service;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.vouchers.vo.Voucher;

public interface VoucherService {
	/**
	 * 通过用户获取代金劵列表
	 * @return
	 */
	public PageInfo<Voucher> getVoucherListByUid(String userId, Pagination pagination);
	
	/**
	 * 通过优惠卷ID获取代金劵详情
	 * @param id
	 * @return
	 */
	public Voucher getVoucherById(String id);
	
	/**
	 * 添加保存代金劵
	 * @param Voucher
	 * @return
	 */
	public Integer saveVoucher(Voucher voucher);
	
	/**
	 * 通过ID删除代金劵
	 * @param id
	 * @return
	 */
	public Integer deleteVoucherById(String id);
}
