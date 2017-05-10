package com.whayer.wx.login.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.X;
import com.whayer.wx.common.encrypt.MD5;
import com.whayer.wx.common.io.FileUtil;
import com.whayer.wx.common.mvc.BaseVerificationController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.common.page.HtmlParser;
import com.whayer.wx.login.service.CompanyService;
import com.whayer.wx.login.service.UserService;
import com.whayer.wx.login.vo.Company;
import com.whayer.wx.login.vo.Role;
import com.whayer.wx.login.vo.User;

/**
 * 登陆与验证
 * @author duyu
 * @since  20-03-17
 */
@RequestMapping(value = "/user")
@Controller
public class LoginController extends BaseVerificationController {

	private final static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Resource 
	private UserService userService;
	
	@Resource
	private CompanyService companyService;

	
	@RequestMapping(value = "/login/verify", method = RequestMethod.GET)
	public void getVerifyCodeImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("LoginController.getVerifyCodeImg()");
		Box box = loadNewBox(request);
		response.setHeader("Progma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		outputVerification(box, response);
	}
	
	@RequestMapping(value = "/getUUID", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getUUID(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("LoginController.getUUID()");
		
		ResponseCondition res = getResponse(X.TRUE);
		
		List<String> list = new ArrayList<>();
		list.add(X.uuidPure8Bit());
		res.setList(list);
		return res;
	}
	
	@RequestMapping(value = "/login/company", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition loginCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("LoginController.loginCompany()");
		
		Box box = loadNewBox(request);
		String code = box.$p("code");
		
		if(isNullOrEmpty(code)){
			return getResponse(X.FALSE);
		}
		
		Company company = companyService.findByCode(code);
		
		if(null == company){
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("没有此集团用户");
			return res;
		}
		
		ResponseCondition res = getResponse(true);
		List<Company> list = new ArrayList<>();
		list.add(company);
		res.setList(list);
		return res;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("LoginController.login()");
		
		Box box = loadNewBox(request);
		if (!X.POST.equalsIgnoreCase(request.getMethod())) {
			return getResponse(false);
		}
		String userName = box.$p(X.USER_NAME);
		String passWord = box.$p(X.PASSWORD);
		// 无需验证 验证码
		//String cookie = box.$c(X.ENCRYPTED + X.KEY).getValue();
		// String verifyCode = box.$p("verifycode");
		userName = (userName == null || userName.isEmpty()) ? "" : userName.trim();
		passWord = (passWord == null || passWord.isEmpty()) ? "" : MD5.md5Encode(passWord.trim());
		/**
		 * 123456 : 49ba59abbe56e057                 (16 bit)
		 *        : e10adc3949ba59abbe56e057f20f883e (32 bit)
		 */
		
		/**
		 * 20-03-17 duyu
		 * 1.若有验证码: 
		 *   验证码携带三个cookie {key: X.ENCRYPTED+X.KEY , value: '验证码的值'} 
		 *                     {key: X.ENCRYPTED+X.TIME, value: '当前时间戳'} 用于验证验证码是否过期
		 *                     {key: X.ENCRYPTED+"hash", value: MD5.md5Encode(MD5.md5Encode(key.toLowerCase()+X.USER_PASS_PREFIX)+X.USER_PASS_PREFIX+time)}
		 *                     //验证hash签名,避免暴力注册与登陆
		 *   预定义错误码 enum:
		 *                     0:正常;1：用户名账号或密码不能为空;2:没有此用户;3:密码错误;4:验证码过期;5.验证码错误;6:非法登录
		 * 2.登陆成功:
		 *   设置三个会话 cookie: {key: X.USERID   ,  value: '用户ID'}
		 *                     {key: X.USER_TYPE,  value: '用户类型'}
		 *                     {key: X.SESSION_ID, value: MD5.md5Encode(X.uuid())}
		 *   设置三个会话 session:{key: X.USER   ,  value: 用户对象SKUser}                //当前用户
		 *                     {key: X.SESSION_ID,  value: MD5.md5Encode(X.uuid())}   //拦截器登陆验证
		 *                     //若需支持分布式,需将session移至第三方Redis服务
		 *                     
		 */

		//int errorCode = 0;
		if (userName.isEmpty() || userName.isEmpty()) {
			log.info("账号密码不能为空");
			//errorCode = 1;
			ResponseCondition res = getResponse(false);
			res.setErrorMsg("账号密码不能为空");
			return res;
		} else {
			User u = new User();
			u.setUsername(userName);
			u.setPassword(passWord);
			User user = userService.findUserByName(userName);
			if (null == user) {
				log.info("没有此用户");
				//errorCode = 2;
				ResponseCondition res = getResponse(false);
				res.setErrorMsg("没有此用户");
				return res;
			} else {
				//user = userService.findUser(u);
				if (user.getPassword() != passWord) {
					log.info("密码错误");
					//errorCode = 3;
					ResponseCondition res = getResponse(false);
					res.setErrorMsg("密码错误");
					return res;
				} else {
					
					if(!user.getIsAudit()){
						ResponseCondition res = getResponse(false);
						res.setErrorMsg("账户正在审核中");
						return res;
					}
					
					// TODO update login time /IP /last_session
					HtmlParser.GetClientIp(request);
		            String sessionid = MD5.md5Encode(X.uuid());

					Cookie uidc = new Cookie(X.USERID, user.getId().toString());
					uidc.setMaxAge(60 * 60 * 6);
					box.getCookie().put(X.USERID, uidc);
					Cookie userType = new Cookie(X.USER_TYPE, Boolean.toString(user.getIsAgent()));
					userType.setMaxAge(60 * 60 * 6);
					box.getCookie().put(X.USER_TYPE, userType);
					Cookie sessioncookie = new Cookie(X.SESSION_ID, user.getId().toString());
					sessioncookie.setMaxAge(60 * 60 * 6);
					box.getCookie().put(X.SESSION_ID, sessioncookie);
					
					writeCookies(box, response);

					request.getSession().setAttribute(X.USER, user);
					request.getSession().setAttribute(X.SESSION_ID, sessionid);
					//request.getSession().setAttribute(X.USER, user.getId().toString());
					//request.getSession().setAttribute(X.USER_TYPE, user.getIsAgent().toString());
					//request.getSession().setAttribute(X.USER_NAME, user.getUsername());

					ResponseCondition res = getResponse(X.TRUE);
					List<User> list = new ArrayList<>();
					list.add(user);
					res.setList(list);
					
					return res;
				}
			}
		}
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public ResponseCondition signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Box box = loadNewBox(request);
		Cookie cu = new Cookie(X.USERID, null);
		Cookie ct = new Cookie(X.USER_TYPE, null);
		Cookie cs = new Cookie(X.SESSION_ID, null);
		cu.setMaxAge(0);
		ct.setMaxAge(0);
		cs.setMaxAge(0);
		box.getCookie().put(X.USERID, cu);
		box.getCookie().put(X.USER_TYPE, ct);
		box.getCookie().put(X.SESSION_ID, cs);
		writeCookies(box, response);
		request.getSession().setAttribute(X.USER, null);
		request.getSession().setAttribute(X.SESSION_ID, null);
		return new ResponseCondition();
	}
	
	/**
	 * 注册代理用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register/agent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition registerAgent(
			@RequestParam(value = "file", required = false) MultipartFile file, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("LoginController.registerAgent()");
		
		Box box = loadNewBox(request);
		String id = X.uuidPure();
		
		
		String username = box.$p(X.USER_NAME);
		String password = box.$p(X.PASSWORD);
		//String userType = box.$p("userType"); //集团用户已独立出来
		String mobile = box.$p("mobile");
		String pid = box.$p("pid"); //父级代理电话/ID
		
		if(isNullOrEmpty(username) || isNullOrEmpty(password) 
				|| isNullOrEmpty(mobile) || isNullOrEmpty(file)){
			return getResponse(X.FALSE);
		}
		
		User user = new User();
		user.setId(id);
		user.setUsername(username.trim());
		user.setPassword(MD5.md5Encode(password.trim()));
		user.setMobile(mobile);
		user.setUserType(0); //0:代理用户 1:集团用户
		user.setpId(pid);
		
		User u = userService.findUser(user);
		if(isNullOrEmpty(u)){
			//http://www.cnblogs.com/fjsnail/p/3491033.html
//			<form action="/user/register/agent" method="post" enctype="multipart/form-data">  
//			  <input type="file" name="file" />  
//			  <input type="submit" value="上传" />  
//			</form> 
			String originFileName = file.getOriginalFilename();
			String extension = FileUtil.getExtension(originFileName);
			originFileName = FileUtil.getFileNameWithOutExtension(originFileName);
			Pattern pattern = Pattern.compile(X.REGEX);
			Matcher matcher = pattern.matcher(originFileName);
			if (matcher.find()) {
				originFileName = matcher.replaceAll("_").trim();
			}
			originFileName = X.uuidPure8Bit()/*originFileName*/ + X.DOT + extension;
			
			ResponseCondition res = getResponse(X.FALSE);
			if (file.getSize() == 0 || file.isEmpty()) {
				log.error("文件不能为空");
				res.setErrorMsg("文件不能为空");
				return res;
			}
			// check if too large
			int maxSize = X.string2int(X.getConfig("file.upload.max.size"));
			if (file.getSize() > maxSize) {
				log.error("文件太大");
				res.setErrorMsg("文件太大");
				return res;
			}
			
			String uploadPath = X.getConfig("file.upload.dir");
			X.makeDir(uploadPath + "/header");
			File targetFile = new File(uploadPath, originFileName);
		    file.transferTo(targetFile);
		    
			//设置头像路径
		    user.setHeadImg(originFileName);
		    
			userService.saveUser(user);
			return getResponse(X.TRUE);
		}
		else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("用户已存在!");
			return res;
		}
	}
	
	/**
	 * 注册集团用户(同时需要添加角色)
	 * 为了产品打标时能单独关联集团用户
	 * name & code
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/register/company")
	@ResponseBody
	public ResponseCondition registerItd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("LoginController.registerItd()");
		
		Box box = loadNewBox(request);
		
		String name = box.$p("name"); 
		String code = box.$p("code");
		
		
		if(isNullOrEmpty(name) || isNullOrEmpty(code)){
			return getResponse(X.FALSE);
		}
		
		//MD5加密
		code = MD5.md5Encode(code.trim());
		
		Company company = new Company();
		company.setId(X.uuidPure());
		company.setName(name.trim());
		company.setCode(code);
		
		Role role = new Role();
		role.setId(X.uuidPure());
		role.setName(name.trim());
		role.setCode(code);
		
		Company c = companyService.findByCode(MD5.md5Encode(code.trim()));
		if(isNullOrEmpty(c)){
			//注册集团用户时,需要添加此集团角色
			companyService.save(company, role);
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("集团用户已存在!");
			return res;
		}
	}
	
	/**
	 * 验证父级代理是否存在
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/validatePid")
	@ResponseBody
	public ResponseCondition validatePid(HttpServletRequest request, HttpServletResponse response){
		log.info("LoginController.validatePid()");
		
		Box box = loadNewBox(request);
		
		String pid = box.$p("pid");
		
		if(isNullOrEmpty(pid)){
			return getResponse(X.FALSE);
		}
		
		if(userService.validatePid(pid)){
			return getResponse(X.TRUE);
		}else{
			ResponseCondition res = getResponse(X.FALSE);
			res.setErrorMsg("父级代理商不存在");
			log.error("父级代理商不存在");
			return res;
		}
	}
	
	@RequestMapping("/getUserListByType")
	@ResponseBody
	public ResponseCondition getListByType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("LoginController.getListByType()");
		
		Box box = loadNewBox(request);
		
		/**
		 * isAgent  0:个人代理 1:区域代理 null:所有代理用户
		 */
		Integer type = null;
		String isAgent = box.$p("isAgent");
		if(isNullOrEmpty(isAgent)){
			type = null;
		}else {
			type = Integer.parseInt(isAgent);
		}
		
		PageInfo<User> pi = userService.getUserListByType(type, box.getPagination());
		
		return pagerResponse(pi);
	}
	
	/**
	 * 批量审批注册用户(只有代理用户需要审批)
	 * @RequestParam("ids") String[] ids    处理简单类型
	 * @see   http://blog.csdn.net/qq_27093465/article/details/50519444
	 * @param ids
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/approval/audit", method = RequestMethod.POST)
	@ResponseBody
	//@RequestParam("ids[]") List<String>也可以
	public ResponseCondition approvalAudit(@RequestParam("ids[]") String[] ids, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("LoginController.approvalAudit()");
		
		if(isNullOrEmpty(ids)){
			getResponse(X.FALSE);
		}
		
		userService.approveAuditBatch(ids);
		
		return getResponse(X.TRUE);
	}

	/**
	 * 批量审批区代
	 * @param ids
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/approval/agent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition approvalAgent(@RequestParam("ids[]") String[] ids, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("LoginController.approvalAgent()");
		
		if(isNullOrEmpty(ids)){
			getResponse(X.FALSE);
		}
		
		userService.approveAgentBatch(ids);
		
		return getResponse(X.TRUE);
	}
	
	/**
	 * 获取代理商下线
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/team", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition team(@RequestParam("userId") String userId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("LoginController.team()");
		
		Box box = loadNewBox(request);
		
		if(isNullOrEmpty(userId)){
			getResponse(X.FALSE);
		}
		
		PageInfo<User> pi = userService.getTeams(userId, box.getPagination());
		
		return pagerResponse(pi);
	}
	
	@RequestMapping(value = "/getUserByName", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition findUserByName(@RequestParam("username") String username, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("LoginController.findUserByName()");
		
		if(isNullOrEmpty(username)){
			getResponse(X.FALSE);
		}
		
		User user = userService.findUserByName(username);
		
		ResponseCondition res = getResponse(X.TRUE);
		List<User> list = new ArrayList<>();
		list.add(user);
		res.setList(list);
		
		return res;
	}
	
}
