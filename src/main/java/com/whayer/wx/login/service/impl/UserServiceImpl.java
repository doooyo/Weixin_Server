package com.whayer.wx.login.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
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
	public int updateUserById(SkUser user) {
		return userDao.updateUserById(user);
	}

	@Override
	public boolean isUserNameExist(String userName) {
		SkUser user = userDao.isUserNameExist(userName);
		if(null != user){
			return true;
		}
		return false;
	}

	@Override
	public boolean isMobileExist(String mobile) {
		SkUser user = userDao.isMobileExist(mobile);
		if(null != user){
			return true;
		}
		return false;
	}

	@Override
	public int saveUser(SkUser user) {
		return userDao.saveUser(user);
	}

	@Override
	public PageInfo<SkUser> getUserListByType(Integer type, Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<SkUser> list = userDao.getUserListByType(type);
		PageInfo<SkUser> pageInfo = new PageInfo<SkUser>(list, pagination.getNavigationSize());
		return pageInfo;
	}

}
