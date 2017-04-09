package com.whayer.wx.login.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.whayer.wx.base.UnitTestBase;
import com.whayer.wx.login.vo.SkUser;

@RunWith(BlockJUnit4ClassRunner.class)
public class UserServiceTest extends UnitTestBase{

	public UserServiceTest() {
		super("classpath:IOC.xml");
	}

	@Test
	public void testUserService() {
		UserService userService = super.getBean("userServiceImpl");
		SkUser user = userService.findUserByName("test");
		
		SkUser u = new SkUser();
		u.setPassword("e10adc3949ba59abbe56e057f20f883e");
		SkUser user1 = userService.findUser(u);
		System.out.println(user1.toString());
	}
}
