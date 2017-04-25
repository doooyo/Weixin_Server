package com.whayer.wx.order.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.order.service.ExamineeService;
import com.whayer.wx.order.vo.Examinee;

@RequestMapping(value = "/examinee")
@Controller
public class ExamineeController extends BaseController{
	private final static Logger log = LoggerFactory.getLogger(ExamineeController.class);
	
	@Resource
	private ExamineeService examineeService;
	
	@RequestMapping("/getList")
	@ResponseBody
	public ResponseCondition getList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("ExamineeController.getList()");
		
		Box box = loadNewBox(request);
		
		PageInfo<Examinee> pi = examineeService.getExamineeList(box.getPagination());
		
		return pagerResponse(pi);
	}
	
	@RequestMapping("/findById")
	@ResponseBody
	public ResponseCondition findById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("ExamineeController.getById()");
		
		Box box = loadNewBox(request);
		
		String id = box.$p("id");
		if(null == id){
			return getResponse(false);
		}
		
		Examinee examinee = examineeService.getExamineeById(id);
		
		ResponseCondition res = getResponse(true);
		List<Examinee> list = new ArrayList<>();
		list.add(examinee);
		res.setList(list);
		return res;
	}
	
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition deleteById(HttpServletRequest request, HttpServletResponse response){
		log.info("ExamineeController.deleteById()");
		Box box = loadNewBox(request);
		
		
		String id = box.$p("id");
		if(null == id){
			return getResponse(false);
		}
		
		int count = examineeService.deleteExamineeById(id);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("删除体检人失败");
			log.error("删除体检人失败");
			return res;
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition save(HttpServletRequest request, HttpServletResponse response){
		log.info("ExamineeController.save()");
		Box box = loadNewBox(request);
		
		String id = X.uuidPure();
		String name = box.$p("name");
		String age = box.$p("age");
		String gender = box.$p("gender");
		String address = box.$p("address");
		String mobile = box.$p("mobile");
		String identityId = box.$p("identityId");
		String birthday = box.$p("birthday");
		
		if(isNullOrEmpty(name) || isNullOrEmpty(gender) || isNullOrEmpty(address) 
				|| isNullOrEmpty(mobile) || isNullOrEmpty(identityId) || isNullOrEmpty(birthday)){
			return getResponse(false);
		}
		boolean sex = gender.equals("0") ? false : true; // 0:男 1:女
		Examinee examinee = new Examinee();
		examinee.setId(id);
		examinee.setName(name);
		examinee.setGender(sex);
		examinee.setAddress(address);
		examinee.setMobile(mobile);
		examinee.setIdentityId(identityId);
		examinee.setBirthday(X.string2date(birthday, X.TIMED));
		if(!isNullOrEmpty(age)){
			examinee.setAge(X.string2int(age));
		}
		
		int count = examineeService.saveExaminee(examinee);
		
		if(count > 0){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("保存体检人失败");
			log.error("保存体检人失败");
			return res;
		}
	}

}
