package com.whayer.wx.login.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.login.vo.Role;

@Repository
public interface RoleDao extends DAO{
	
	public List<Role> getRoleList();
	
	public Role findById(@Param("id") String id);
	
	public Role findByCode(@Param("code") String code);
	
	public int save(Role role);
	
	public int update(Role role);
	
	public int deleteById(@Param("id") String id);

}
