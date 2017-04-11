package com.whayer.wx.order.service;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.order.vo.Examinee;

public interface ExamineeService {
	/**
	 * 获取体检者列表
	 * @return
	 */
	public PageInfo<Examinee> getExamineeList(Pagination pagination);
	
	/**
	 * 通过产品ID获取体检者详情
	 * @param id
	 * @return
	 */
	public Examinee getExamineeById(String id);
	
	/**
	 * 保存体检者
	 * @param product
	 * @return
	 */
	public Integer saveExaminee(Examinee examinee);
	
	/**
	 * 通过ID删除体检者
	 * @param id
	 * @return
	 */
	public Integer deleteExamineeById(String id);
}
