package com.whayer.wx.login.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.login.dao.RoleDao;
import com.whayer.wx.login.service.RoleService;
import com.whayer.wx.login.vo.Role;

@Service
public class RoleServiceImpl implements RoleService{
	@Resource
	private RoleDao roleDao;

	@Override
	public PageInfo<Role> getRoleList(Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Role> list = roleDao.getRoleList();
		PageInfo<Role> pageInfo = new PageInfo<Role>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	@Override
	public List<Role> getAllRoleList() {
		return roleDao.getRoleList();
	}

	@Override
	public Role getById(String id) {
		return roleDao.findById(id);
	}

	@Override
	public int save(Role role) {
		return roleDao.save(role);
	}

	@Override
	public int deleteById(String id) {
		return roleDao.deleteById(id);
	}

	@Override
	public int update(Role role) {
		return roleDao.update(role);
	}

}
