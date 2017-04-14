package com.whayer.wx.login.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.login.dao.CompanyDao;
import com.whayer.wx.login.dao.RoleDao;
import com.whayer.wx.login.service.CompanyService;
import com.whayer.wx.login.vo.Company;
import com.whayer.wx.login.vo.Role;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Resource
	private CompanyDao companyDao;
	
	@Resource
	private RoleDao roleDao;
	
	@Override
	public PageInfo<Company> getCompanyList(Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Company> list = companyDao.getCompanyList();
		PageInfo<Company> pageInfo = new PageInfo<Company>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	@Override
	public List<Company> getAllCompanyList() {
		return companyDao.getCompanyList();
	}

	@Override
	public Company findById(String id) {
		return companyDao.findById(id);
	}
	
	@Override
	public Company findByCode(String code) {
		return companyDao.findByCode(code);
	}

	@Override
	public int update(Company company, Role role) {
		int updateCompany = companyDao.update(company);
		int updateRole =  roleDao.update(role);
		return updateCompany + updateRole;
	}

	@Override
	public int deleteById(String id) {
		int deleteCompany = companyDao.deleteById(id);
		int deleteRole = roleDao.deleteById(id);
		return deleteCompany + deleteRole;
	}

	@Override
	public int save(Company company, Role role) {
		int saveCompany = companyDao.save(company);
		int saveRole = roleDao.save(role);
		return saveCompany + saveRole;
	}
}
