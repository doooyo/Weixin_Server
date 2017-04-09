package com.whayer.wx.vouchers.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.vouchers.dao.VoucherDao;
import com.whayer.wx.vouchers.service.VoucherService;
import com.whayer.wx.vouchers.vo.Voucher;

@Service
public class VoucherServiceImpl implements VoucherService{
	
	@Resource
	private VoucherDao voucherDao;

	@Override
	public PageInfo<Voucher> getVoucherListByUid(String userId, Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Voucher> list = voucherDao.getVoucherListByUid(userId);
		PageInfo<Voucher> pageInfo = new PageInfo<Voucher>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	@Override
	public Voucher getVoucherById(String id) {
		return voucherDao.getVoucherById(id);
	}

	@Override
	public Integer saveVoucher(Voucher voucher) {
		return voucherDao.saveVoucher(voucher);
	}

	@Override
	public Integer deleteVoucherById(String id) {
		return voucherDao.deleteVoucherById(id);
	}

}
