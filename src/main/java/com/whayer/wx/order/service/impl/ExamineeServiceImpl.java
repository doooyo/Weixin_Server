package com.whayer.wx.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.order.dao.ExamineeDao;
import com.whayer.wx.order.service.ExamineeService;
import com.whayer.wx.order.vo.Examinee;

@Service
public class ExamineeServiceImpl implements ExamineeService{
	@Resource
	private ExamineeDao examineeDao;

	@Override
	public PageInfo<Examinee> getExamineeList(Pagination pagination) {
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		List<Examinee> list =  examineeDao.getExamineeList();
		PageInfo<Examinee> pageInfo = new PageInfo<Examinee>(list, pagination.getNavigationSize());
		return pageInfo;
	}

	@Override
	public Examinee getExamineeById(String id) {
		return examineeDao.getExamineeById(id);
	}

	@Override
	public Integer saveExaminee(Examinee examinee) {
		return examineeDao.saveExaminee(examinee);
	}

	@Override
	public Integer deleteExamineeById(String id) {
		return examineeDao.deleteExamineeById(id);
	}

}
