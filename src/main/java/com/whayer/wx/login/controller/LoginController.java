package com.whayer.wx.login.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
		
		Company company = companyService.findByCode(code.trim());
		
		ResponseCondition errRes = getResponse(X.FALSE);
		
		if(isNullOrEmpty(company)){
			errRes.setErrorMsg("没有此集团用户");
			return errRes;
		}
		
		Date beginTime = company.getActiveBeginTime();
		Date endTime = company.getActiveEndTime();
		
		if(!isNullOrEmpty(beginTime) && !isNullOrEmpty(endTime)){
			if(beginTime.compareTo(new Date()) > 0){
				errRes.setErrorMsg("不在活动期间");
				return errRes;
			}
			if(endTime.compareTo(new Date()) < 0){
				errRes.setErrorMsg("活动已过期");
				return errRes;
			}
		}
		
		ResponseCondition res = getResponse(true);
		List<Company> list = new ArrayList<>();
		list.add(company);
		res.setList(list);
		return res;
	}
	
	@RequestMapping(value = "/weixin/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition weixinLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("LoginController.weixinLogin()");
		
		Box box = loadNewBox(request);
		String userName = box.$p(X.USER_NAME);
		String passWord = box.$p(X.PASSWORD);
		// 无需验证 验证码
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
		if (userName.isEmpty() || passWord.isEmpty()) {
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
				if (!user.getPassword().equals(passWord)) { //e10adc3949ba59abbe56e057f20f883e
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
					String sessionid = X.uuid(); //aes.encrypt(X.uuid());
		            String userId = user.getId();

					Cookie uidc = new Cookie(X.ENCRYPTED + X.USERID, userId);
					uidc.setMaxAge(60 * 60 * 6);
					box.getCookie().put(X.USERID, uidc);
					Cookie userType = new Cookie(X.ENCRYPTED + X.USER_TYPE, Boolean.toString(user.getIsAgent()));
					userType.setMaxAge(60 * 60 * 6);
					box.getCookie().put(X.USER_TYPE, userType);
					Cookie sessioncookie = new Cookie(X.ENCRYPTED + X.SESSION_ID, sessionid);
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

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("LoginController.login()");
		
		Box box = loadNewBox(request);
//		if (!X.POST.equalsIgnoreCase(request.getMethod())) {
//			return getResponse(false);
//		}
		String userName = box.$p(X.USER_NAME);
		String passWord = box.$p(X.PASSWORD);
		// 无需验证 验证码
		String cookie = box.$c(X.ENCRYPTED + X.KEY).getValue();
		String verifyCode = box.$p("verifyCode");
		String loginType = box.$p("loginType");
		userName = (userName == null || userName.isEmpty()) ? "" : userName.trim();
		passWord = (passWord == null || passWord.isEmpty()) ? "" : MD5.md5Encode(passWord.trim());
		
		
		if(!isNullOrEmpty(loginType) && loginType.equals("1")){
			//PC登陆
			ResponseCondition res = getResponse(X.FALSE);
			if(isNullOrEmpty(verifyCode) || isNullOrEmpty(cookie)){
				res.setErrorMsg("请填写验证码");
				return res;
			}
			
			if(!verifyCode.equalsIgnoreCase(cookie)){
				res.setErrorMsg("验证码错误");
				return res;
			}
		}else{
			//小程序登陆
		}
		
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
		if (userName.isEmpty() || passWord.isEmpty()) {
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
				if (!user.getPassword().equals(passWord)) { //e10adc3949ba59abbe56e057f20f883e
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
					
		            String sessionid = X.uuid(); //aes.encrypt(X.uuid());
		            String userId = user.getId();

					Cookie uidc = new Cookie(X.ENCRYPTED + X.USERID, userId);
					uidc.setMaxAge(60 * 60 * 6);
					box.getCookie().put(X.USERID, uidc);
					Cookie userType = new Cookie(X.ENCRYPTED + X.USER_TYPE, Boolean.toString(user.getIsAgent()));
					userType.setMaxAge(60 * 60 * 6);
					box.getCookie().put(X.USER_TYPE, userType);
					Cookie sessioncookie = new Cookie(X.ENCRYPTED + X.SESSION_ID, sessionid);
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
					user.setPassword(null);
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
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
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
		return getResponse(X.TRUE);
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
			@RequestParam(value = "files", required = false) MultipartFile[] files, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("LoginController.registerAgent()");
		
		Box box = loadNewBox(request);
		String id = X.uuidPure();
		
		
		String username = box.$p(X.USER_NAME);
		String password = box.$p(X.PASSWORD);
		//String userType = box.$p("userType"); //集团用户已独立出来
		String mobile = box.$p("mobile");
		String pid = box.$p("pid"); //父级代理电话/ID
		String nickName = box.$p("nickName"); 
		
	    String email = box.$p("email");         //邮件
	    String idCardNo = box.$p("idCardNo");   //身份证ID
	    String bank = box.$p("bank");           //开户银行
	    String bankCardNo = box.$p("bankCardNo");//银行卡号
	    //String idCardImg = box.$p("idCardImg");  //身份证图片 
	    String bankCardName = box.$p("bankCardName"); //银行卡户名
	    String address = box.$p("address"); //收件地址
		
		if(isNullOrEmpty(username) || isNullOrEmpty(password) 
				|| isNullOrEmpty(mobile) || isNullOrEmpty(files) || files.length == 0
				|| isNullOrEmpty(email) || isNullOrEmpty(nickName)
				|| isNullOrEmpty(idCardNo) || isNullOrEmpty(bank) || isNullOrEmpty(bankCardNo)
				|| isNullOrEmpty(bankCardName) || isNullOrEmpty(address)){
			return getResponse(X.FALSE);
		}
		
		User user = new User();
		user.setId(id);
		user.setUsername(username.trim());
		user.setPassword(MD5.md5Encode(password.trim()));
		user.setMobile(mobile);
		user.setUserType(0); //0:代理用户 1:集团用户
		//user.setpId(pid);
		user.setNickName(nickName);
		
		user.setEmail(email);
		user.setBank(bank);
		user.setIdCardNo(idCardNo);
		user.setBankCardNo(bankCardNo);
		
		user.setBankCardName(bankCardName);
		user.setAddress(address);
		
		User u = userService.findUser(user);
		ResponseCondition res = getResponse(X.FALSE);
		
		if(isNullOrEmpty(u)){
			//http://www.cnblogs.com/fjsnail/p/3491033.html
//			<form action="/user/register/agent" method="post" enctype="multipart/form-data">  
//			  <input type="file" name="file" />  
//			  <input type="submit" value="上传" />  
//			</form> 
			if(!isNullOrEmpty(files) && files.length > 0){
				List<String> list = new ArrayList<>();
				for(int i = 0; i < files.length; i++){
					
					String originFileName = files[i].getOriginalFilename();
					String extension = FileUtil.getExtension(originFileName);
					originFileName = FileUtil.getFileNameWithOutExtension(originFileName);
					Pattern pattern = Pattern.compile(X.REGEX);
					Matcher matcher = pattern.matcher(originFileName);
					if (matcher.find()) {
						originFileName = matcher.replaceAll("_").trim();
					}
					originFileName = X.uuidPure8Bit()/*originFileName*/ + X.DOT + extension;
					
					if (files[i].getSize() == 0 || files[i].isEmpty()) {
						log.error("文件不能为空");
						res.setErrorMsg("文件不能为空");
						return res;
					}
					// check if too large
					int maxSize = X.string2int(X.getConfig("file.upload.max.size"));
					if (files[i].getSize() > maxSize) {
						log.error("文件太大");
						res.setErrorMsg("文件太大");
						return res;
					}
					
					String uploadPath = X.getConfig("file.upload.dir");
					uploadPath += "/idcard";//header
					X.makeDir(uploadPath);
					File targetFile = new File(uploadPath, originFileName);
				    files[i].transferTo(targetFile);
				    list.add(originFileName);
				}
				
			    String imgs = StringUtils.join(list, "|");
				//设置头像路径
			    user.setIdCardImg(imgs);
			}
			
			//获取父级pid指定的父级用户
			if(!isNullOrEmpty(pid)){
				User user2 = userService.findUserByPid(pid);
				if(!isNullOrEmpty(user2)){
					user.setpId(user2.getId());
				}
			}
			
		    
			int result = userService.saveUser(user);
			if(result > 0)
				return getResponse(X.TRUE);
			else{
				res.setErrorMsg("更新代理商失败!");
				return res;
			}
		}
		else{
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
	@RequestMapping(value = "/register/company", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition registerItd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("LoginController.registerItd()");
		
		Box box = loadNewBox(request);
		
		String name = box.$p("name"); 
		String code = box.$p("code");
		
		
		if(isNullOrEmpty(name) || isNullOrEmpty(code)){
			return getResponse(X.FALSE);
		}
		name = name.trim();
		code = code.trim();
		
		Company c = companyService.findByCode(code);
		if(isNullOrEmpty(c)){
			String id = X.uuidPure();
			Company company = new Company();
			company.setId(id);
			company.setName(name);
			company.setCode(code);
			
			Role role = new Role();
			role.setId(id);
			role.setName(name);
			role.setCode(code);
			
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
	@RequestMapping(value = "/validatePid", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/getUserListByType", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getListByType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("LoginController.getListByType()");
		
		Box box = loadNewBox(request);
		
		/**
		 * isAudit  0:未审核通过, 1:已审核通过
		 */
		Integer isAuditType = null;
		String isAudit = box.$p("isAudit");
		String nickName = box.$p("name");
		
		if(!isNullOrEmpty(isAudit)){
			isAuditType = Integer.parseInt(isAudit);
		}
		if(!isNullOrEmpty(nickName)) nickName = nickName.trim();
		
		PageInfo<User> pi = userService.getUserListByType(isAuditType, nickName, box.getPagination());
		
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
	
	@RequestMapping(value = "/changePwd", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition changePwd(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("LoginController.changePwd()");
	
		Box box = loadNewBox(request);
		
		String username = box.$p("username");
		String oldPwd = box.$p("oldPassword");
		String newPwd = box.$p("password");
		
		if(isNullOrEmpty(username) || isNullOrEmpty(oldPwd) || isNullOrEmpty(newPwd)){
			return getResponse(X.FALSE);
		}
		
		User user = userService.findUserByName(username);
		ResponseCondition res = getResponse(X.FALSE);
		if(isNullOrEmpty(user)){
			res.setErrorMsg("没有此用户");
			return res;
		}
		if(!user.getPassword().equals(MD5.md5Encode(oldPwd.trim()))){
			res.setErrorMsg("旧密码错误");
			return res;
		}
		
		user.setPassword(MD5.md5Encode(newPwd.trim()));
		int result = userService.updateUserById(user);
		
		if(result > 0){
			return getResponse(X.TRUE);
		}else return getResponse(X.FALSE);
	}
	
	/**
	 * 更新代理商用户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAgent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition updateAgent(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		log.info("LoginController.updateAgent()");
		
		Box box = loadNewBox(request);
		
		String mobile = box.$p("mobile");
		String nickName = box.$p("nickName");
		String id = box.$p("id");
		
		String bankCardName = box.$p("bankCardName"); 
	    String address = box.$p("address"); 
	    
		if(isNullOrEmpty(id) || isNullOrEmpty(mobile) || isNullOrEmpty(address)
				|| isNullOrEmpty(nickName) || isNullOrEmpty(bankCardName)){
			return getResponse(X.FALSE);
		}
		
		User user = new User();
		user.setId(id);
		user = userService.findUser(user);
		//user.setMobile(mobile);
		//user.setNickName(nickName);
		ResponseCondition res = getResponse(X.FALSE);
		if(isNullOrEmpty(user)){
			res.setErrorMsg("没有此用户!");
			return res;
		}
		
		if(!isNullOrEmpty(file) && !file.isEmpty() && file.getSize()  > 0){
			String originFileName = file.getOriginalFilename();
			String extension = FileUtil.getExtension(originFileName);
			originFileName = FileUtil.getFileNameWithOutExtension(originFileName);
			Pattern pattern = Pattern.compile(X.REGEX);
			Matcher matcher = pattern.matcher(originFileName);
			if (matcher.find()) {
				originFileName = matcher.replaceAll("_").trim();
			}
			originFileName = X.uuidPure8Bit()/*originFileName*/ + X.DOT + extension;
			
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
			uploadPath += "/header";
			X.makeDir(uploadPath);
			File targetFile = new File(uploadPath, originFileName);
		    file.transferTo(targetFile);
		    
		    //删除旧头像
		    File oldFile = new File(uploadPath, user.getHeadImg());
		    oldFile.delete();
		    
		    //设置头像信息
		    user.setHeadImg(originFileName);
		}
		
	    user.setMobile(mobile);
	    user.setNickName(nickName);
	    user.setBankCardName(bankCardName);
	    user.setAddress(address);
		
		int result = userService.updateUserById(user);
		if(result > 0){
			return getResponse(X.TRUE);
		}else return getResponse(X.FALSE);
	}
	
//	/**
//	 * 更新集团用户信息
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseCondition updateCompany(HttpServletRequest request, HttpServletResponse response) {
//		log.info("LoginController.updateCompany()");
//		
//		Box box = loadNewBox(request);
//		String id = box.$p("id");
//		String name = box.$p("name");
//		
//		if(isNullOrEmpty(id) || isNullOrEmpty(name)){
//			return getResponse(X.FALSE);
//		}
//		
//		int result = companyService.updateCompanyName(id, name);
//		
//		if(result > 0){
//			return getResponse(X.TRUE);
//		}else return getResponse(X.FALSE);
//	}
	
	/**
	 * 删除代理商
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition deleteUser(HttpServletRequest request, HttpServletResponse response) {
		log.info("LoginController.deleteUser()");
		
		Box box = loadNewBox(request);
		String id = box.$p("id");
		
		if(isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		User user = new User();
		user.setId(id);
		user = userService.findUser(user);
		//String headImg = user.getHeadImg();
		
		int result = userService.deleteUserById(id);
		if(result > 0){
			if(!isNullOrEmpty(user) && !isNullOrEmpty(user.getHeadImg())){
				String uploadPath = X.getConfig("file.upload.dir");
				uploadPath += "/header";
			    
			    //删除头像
			    File oldFile = new File(uploadPath, user.getHeadImg());
			    oldFile.delete();
			}
			
			return getResponse(X.TRUE);
		}else return getResponse(X.FALSE);
	}
	
	
	/**
	 * 删除集团用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteCompany", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition deleteCompany(HttpServletRequest request, HttpServletResponse response) {
		log.info("LoginController.deleteCompany()");
		
		Box box = loadNewBox(request);
		String id = box.$p("id");
		
		if(isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		int result = companyService.deleteById(id);
		
		if(result > 0){
			return getResponse(X.TRUE);
		}else return getResponse(X.FALSE);
	}
	
}
