package com.whayer.wx.login.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.login.dao.CompanyDao;
import com.whayer.wx.login.service.CompanyService;
import com.whayer.wx.login.vo.Company;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Resource
	private CompanyDao companyDao;
	
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
		return null;
	}

	@Override
	public int updateById(Company company) {
		return 0;
	}

	@Override
	public int deleteById(String id) {
		return 0;
	}

	@Override
	public Company save(Company company) {
		return null;
	}

	@Override
	public Company findByCode(String code) {
		return companyDao.findByCode(code);
	}

}
