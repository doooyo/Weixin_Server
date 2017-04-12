package com.whayer.wx.order.service;



import org.junit.Test;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.base.UnitTestBase;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.order.vo.Examinee;

public class ExamineeServiceTest extends UnitTestBase{

	public ExamineeServiceTest() {
		super("classpath:IOC.xml");
	}
	
	@Test
	public void testGetExamineeList() {
		ExamineeService examineeService = super.getBean("examineeServiceImpl");
		examineeService.getExamineeList(new Pagination());
		PageInfo<Examinee> pi = examineeService.getExamineeList(new Pagination());
		ResponseCondition res = new ResponseCondition();
		res.setIsSuccess(true);
		res.setList(pi.getList());
		res.setPageIndex(pi.getPageNum());
		res.setPageSize(pi.getPageSize());
		res.setTotal(pi.getTotal());
		res.setPages(pi.getPages());
		System.out.println(res.toString());
	}
	
	@Test
	public void testCreateRows(){
		ExamineeService examineeService = super.getBean("examineeServiceImpl");
		for(int i = 1; i <= 8; i++){
			Examinee p = new Examinee();
			p.setId(X.uuidPure());
			p.setName("体检人" + i);
			p.setAge(26);
			p.setGender(false);
			p.setAddress("成都市高新区天府软件园");
			p.setMobile("15828645446");
			p.setIdentityId("500225198911055674");
			p.setBirthday(X.string2date("2017-04-10", X.TIMED));
			examineeService.saveExaminee(p);
		}
	}

}
