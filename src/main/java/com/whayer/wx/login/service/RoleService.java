package com.whayer.wx.login.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.login.vo.Role;

public interface RoleService {

	public PageInfo<Role> getRoleList(Pagination pagination);
	
	public List<Role> getAllRoleList();
	
	public Role getById(String id);
	
	public int save(Role role);
	
	public int deleteById(String id);
	
	public int update(Role role);
}
