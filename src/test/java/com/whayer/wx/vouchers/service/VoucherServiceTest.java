package com.whayer.wx.vouchers.service;

import org.junit.Test;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.base.UnitTestBase;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.vouchers.vo.Voucher;

public class VoucherServiceTest extends UnitTestBase{

	public VoucherServiceTest() {
		super("classpath:IOC.xml");
	}
	
	@Test
	public void testVoucherService() {
		VoucherService voucherService = super.getBean("voucherServiceImpl");
		PageInfo<Voucher> pi = voucherService.getVoucherListByUid("00010102", new Pagination());
		ResponseCondition res = new ResponseCondition();
		res.setIsSuccess(true);
		res.setList(pi.getList());
		res.setPageIndex(pi.getPageNum());
		res.setPageSize(pi.getPageSize());
		res.setTotal(pi.getTotal());
		res.setPages(pi.getPages());
		System.out.println(res.toString());
	}

}
