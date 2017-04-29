package com.whayer.wx.login.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.login.dao.UserDao;
import com.whayer.wx.login.service.UserService;
import com.whayer.wx.login.vo.User;

@Service
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;
	
	@Override
	public User findUser(User user) {
		
		return userDao.findUser(user);
	}

	@Override
	public User findUserByName(String userName) {
		
		return userDao.findUserByName(userName);
	}

	@Override
	public int updateUserById(User user) {
		return userDao.updateUserById(user);
	}

	@Override
	public boolean isUserNameExist(String userName) {
		User user = userDao.isUserNameExist(userName);
		if(null != user){
			return true;
		}
		return false;
	}

	@Override
	public boolean isMobileExist(String mobile) {
		User user = userDao.isMobileExist(mobile);
		if(null != user){
			return true;
		}
		return false;
	}

	@Override
	public int saveUser(User user) {
		return userDao.saveUser(user);
	}

	@Override
	public PageInfo<User> getUserListByType(Integer type, Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<User> list = userDao.getUserListByType(type);
		PageInfo<User> pageInfo = new PageInfo<User>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	@Override
	public int approveAuditBatch(String... ids) {
		
		return userDao.approveAuditBatch(ids);
	}

	@Override
	public int approveAgentBatch(String... ids) {
		
		return userDao.approveAgentBatch(ids);
	}

	@Override
	public PageInfo<User> getTeams(String userId, Pagination pagination) {
		
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<User> list = userDao.getTeams(userId);
		PageInfo<User> pageInfo = new PageInfo<User>(list, pagination.getNavigationSize());
		return pageInfo;
	}

}
