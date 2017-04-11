package com.whayer.wx.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.whayer.wx.common.mvc.DAO;
import com.whayer.wx.order.vo.Examinee;

@Repository
public interface ExamineeDao extends DAO{
	
	public List<Examinee> getExamineeList();
	
	public Examinee getExamineeById(@Param("id") String id);
	
	public int saveExaminee(Examinee examinee);
	
	public int deleteExamineeById(@Param("id") String id);
	
}
