package com.whayer.wx.login.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.whayer.wx.login.dao.UserDao;
import com.whayer.wx.login.service.UserService;
import com.whayer.wx.login.vo.SkUser;

@Service
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;
	
	@Override
	public SkUser findUser(SkUser user) {
		
		return userDao.findUser(user);
	}

	@Override
	public SkUser findUserByName(String userName) {
		
		return userDao.findUserByName(userName);
	}

	@Override
	public int updateUser(SkUser user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUserNameExist(String userName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMobileExist(String mobile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveUser(SkUser user) {
		// TODO Auto-generated method stub
		return false;
	}

}
