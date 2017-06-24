//package com.whayer.wx.common.filter;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.DispatcherType;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//
//import com.whayer.wx.common.X;
//import com.whayer.wx.common.encrypt.AES;
//import com.whayer.wx.common.mvc.Box;
//import com.whayer.wx.common.mvc.ResponseCondition;
//import com.whayer.wx.login.vo.User;
//
//@WebFilter(servletNames = { "springmvc" }, filterName = "commonFilter", urlPatterns = { "/*" }, dispatcherTypes = {
//		DispatcherType.REQUEST, DispatcherType.ASYNC }, asyncSupported = true)
//public class CommonFilter implements Filter {
//	
//	private String encoding; 
//	private boolean flag;
//	protected AES aes;
//	private final PathMatcher pathMatcher = new AntPathMatcher();
//	private String[] excludeUrlPatterns = {
//			"/user/login/*", "/user/weixin/login", "/user/logout", 
//			"/user/register/*", "/user/validatePid"};
//	
//
//	@Override
//	public void destroy() {
//
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		
//		System.out.println("---经过过滤器前---");
//		
//		//设置字符集
//		if (null != this.encoding && !this.encoding.equals("")) {
//			request.setCharacterEncoding(this.encoding);
//			response.setCharacterEncoding(this.encoding);
//		} else {
//			request.setCharacterEncoding("utf-8");
//			response.setCharacterEncoding("utf-8");
//		}
//		
//		if (isExclude(req)) {
//			flag = true;
//		}else{
//			Box box = new Box();
//			loadCookie(req, box);
//			String sessioncookieid = box.$cv(X.ENCRYPTED + X.SESSION_ID);
//			String userid = box.$cv(X.ENCRYPTED + X.USERID);
//			if(null == sessioncookieid || null == userid){
//				flag =  false;
//			}else{
//				sessioncookieid = aes.decrypt(sessioncookieid);//aes.decrypt(box.$c(X.ENCRYPTED + X.SESSION_ID).getValue()); 
//				userid = aes.decrypt(userid); //aes.decrypt(box.$c(X.ENCRYPTED + X.USERID).getValue()); 
//				String sessionid = (String)req.getSession().getAttribute(X.SESSION_ID);
//				User user = (User)req.getSession().getAttribute(X.USER);
//
//				if (null != user && sessioncookieid.equals(sessionid) && userid.equals(user.getId())) {
//					flag =  true;
//				} else {
//					flag =  false;
//				}
//			}
//		}
//		
//		if(flag){
//			chain.doFilter(request, response);
//		}else{
//			String reqType = req.getHeader("X-Requested-With");
//			if(reqType.equalsIgnoreCase("XMLHttpRequest")) {
//				//TODO 测试前端是否能拿到
//				res.setHeader("sessionstatus","timeout");
//				
//				ResponseCondition rc = new ResponseCondition();
//				rc.setHttpCode(10000);
//				rc.setErrorMsg("登陆超时");
//				rc.setIsSuccess(false);
//				PrintWriter pw = res.getWriter();    
//		        pw.print(rc);    
//		        pw.close(); 
//			}
//			else {
//				//res.getRequestDispatcher("/login").forward(request, response);
//				res.sendRedirect(req.getContextPath() + "/login.jsp");
//			}
//		}
//		
//		System.out.println("---经过过滤器后---");
//	}
//
//	@Override
//	public void init(FilterConfig config) throws ServletException {
//		//获取web.xml中filter参数配置
//		this.encoding = config.getInitParameter("encoding");  
//		
//		this.aes = new AES("2017.03_duyu_chengdu_china");
//	}
//
//	
//	protected void loadCookie(HttpServletRequest request, Box box) {
//		Cookie[] clientCookiesArray = request.getCookies();
//		if (null == clientCookiesArray) {
//			return;
//		}
//		for (Cookie c : clientCookiesArray) {
//			box.getCookie().put(c.getName(), c);
//		}
//	}
//	
//	private boolean isExclude(final HttpServletRequest request) {
//		if (excludeUrlPatterns == null) {
//			return false;
//		}
//		for (String pattern : excludeUrlPatterns) {
//			if (pathMatcher.match(pattern, request.getServletPath())) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//}
