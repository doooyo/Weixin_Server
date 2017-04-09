package com.whayer.wx.login.service;

import java.util.UUID;

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
	
	@Test
	public void testSaveUser(){
		UserService userService = super.getBean("userServiceImpl");
		SkUser user = new SkUser();
		user.setId(UUID.randomUUID().toString());
		user.setpId("");
		user.setIsAgent(0);
		user.setUsername("tom");
		user.setPassword("e10adc3949ba59abbe56e057f20f883e");
		user.setPhoto(null);
		user.setPoints(100L);
		user.setMobile("15828645446");
		user.setAuditState(0);
		int count = userService.saveUser(user);
		System.out.println(count);
	}
	
	@Test
	public void testUpdateUserById(){
		UserService userService = super.getBean("userServiceImpl");
		SkUser user = new SkUser();
		user.setId("00010102");
		user.setpId("be9e9b0c-39df-456b-9223-c03fe0f3e77c");
		user.setIsAgent(1);
		user.setUsername(null);
		user.setPassword(null);
		user.setPhoto(null);
		user.setPoints(100L);
		user.setMobile("15828645447");
		user.setAuditState(1);
		int count = userService.updateUserById(user);
		System.out.println(count);
	}

	@Test
	public void testIsUserNameExist(){
		UserService userService = super.getBean("userServiceImpl");
		boolean isExist = userService.isUserNameExist("doyo");
		System.out.println(isExist);
	}
	
	@Test
	public void testIsMobileExist(){
		UserService userService = super.getBean("userServiceImpl");
		boolean isExist = userService.isMobileExist("15828645446");
		System.out.println(isExist);
	}
	
	@Test
	public void testFindUserByName(){
		UserService userService = super.getBean("userServiceImpl");
		SkUser user = userService.findUserByName("doyo");
		System.out.println(user);
	}
}