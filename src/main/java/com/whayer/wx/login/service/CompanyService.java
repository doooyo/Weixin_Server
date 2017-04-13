package com.whayer.wx.login.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.login.vo.Company;

public interface CompanyService {
	
	public PageInfo<Company> getCompanyList(Pagination pagination);
	
	public List<Company> getAllCompanyList();
	
	public Company findById(String id);
	
	public Company save(Company company);
	
	public int updateById(Company company);
	
	public int deleteById(String id);
	
	public Company findByCode(String code);
}
