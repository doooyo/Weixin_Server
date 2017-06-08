package com.whayer.wx.login.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.login.vo.Company;
import com.whayer.wx.login.vo.Role;

public interface CompanyService {
	
	public PageInfo<Company> getCompanyList(Pagination pagination);
	
	public List<Company> getAllCompanyList();
	
	public Company findById(String id);
	
	public Company findByCode(String code);
	
	public int save(Company company, Role role);
	
	public int update(Company company, Role role);
	
	public int updateCompanyName(String id, String name);
	
	public int deleteById(String id);
}
