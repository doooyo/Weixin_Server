package com.whayer.wx.login.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.login.vo.Company;

@Repository
public interface CompanyDao extends DAO{

	public List<Company> getCompanyList(@Param("name") String name);
	
	public Company findById(@Param("id") String id);
	
	public Company findByCode(@Param("code") String code);
	
	public int save(Company company);
	
	public int update(Company company);
	
	public int deleteById(@Param("id") String id);
}
