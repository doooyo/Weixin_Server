package com.whayer.wx.login.service;

import org.junit.Test;

import com.whayer.wx.base.UnitTestBase;
import com.whayer.wx.common.X;
import com.whayer.wx.login.vo.Company;
import com.whayer.wx.login.vo.Role;

public class CompanyServiceTest extends UnitTestBase{
	
	public CompanyServiceTest() {
		super("classpath:IOC.xml");
	}

	@Test
	public void testSave() {
		CompanyService companyService = super.getBean("companyServiceImpl");
		String name = "索贝数码";
		String code = X.uuidPure8Bit();
		Company company = new Company();
		company.setId(X.uuidPure());
		company.setName(name.trim());
		company.setCode(code);
		
		Role role = new Role();
		role.setId(X.uuidPure());
		role.setName(name.trim());
		role.setCode(code);
		
		int count = companyService.save(company, role);
		System.out.println(count);
	}

}
