package com.whayer.wx.login.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.login.service.RoleService;
import com.whayer.wx.login.vo.Role;

/**
 * 角色管理
 * @author duyu
 * @since  12-04-17
 */
@RequestMapping(value = "/role")
@Controller
public class RoleController extends BaseController{
	
	private final static Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Resource
	private RoleService roleService;
	
	/**
	 * 角色列表(分页)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public ResponseCondition getList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("RoleController.getList()");
		
		Box box = loadNewBox(request);
		
		PageInfo<Role> pi = roleService.getRoleList(box.getPagination());
		
		return pagerResponse(pi);
	}
	
	/**
	 * 角色列表(未分页)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getAllList")
	@ResponseBody
	public ResponseCondition getAllList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("RoleController.getAllList()");
		
		List<Role> list = roleService.getAllRoleList();
		ResponseCondition res = getResponse(true);
		res.setList(list);
		
		return res;
	}
	
//	@RequestMapping("/save")
//	@ResponseBody
//	public ResponseCondition save(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		log.info("RoleController.save()");
//		
//		Box box = loadNewBox(request);
//		String code = box.$p("code");
//		String name = box.$p("name");
//		String id = X.uuidPure();
//		if(isNullOrEmpty(code) || isNullOrEmpty(name)){
//			return getResponse(X.FALSE);
//		}
//		
//		Role role = new Role();
//		role.setId(id);
//		role.setCode(code);
//		role.setName(name);
//		
//		roleService.save(role);
//		
//		return getResponse(X.TRUE);
//	}
}
