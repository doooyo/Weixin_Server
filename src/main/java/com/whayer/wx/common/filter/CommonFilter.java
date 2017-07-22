package com.whayer.wx.common.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.whayer.wx.common.X;
import com.whayer.wx.common.encrypt.AES;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.login.vo.User;

//@WebFilter(servletNames = { "springmvc" }, filterName = "commonFilter", urlPatterns = { "/*" }, dispatcherTypes = {
//		DispatcherType.REQUEST, DispatcherType.ASYNC/*, DispatcherType.FORWARD*/ }, asyncSupported = true)
public class CommonFilter implements Filter {
	
	private String encoding; 
	private boolean flag;
	protected AES aes;
	private final PathMatcher pathMatcher = new AntPathMatcher();
	private String[] excludeUrlPatterns = {
			"/user/login/*", "/user/weixin/login", "/user/logout", 
			"/user/register/*", "/user/validatePid", 
			"/", "/login", "/register", "/static/**/*",
			//若不拦截FOREWARD,下面两个html可以不配置(action返回的虚拟视图默认使用FORWARD进行真实视图的内部转发)
			"/WEB-INF/jsp/login.html", "/WEB-INF/jsp/register.html"};
	
	private FilterConfig config;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String URI = req.getRequestURI();
		
		System.out.println("---" + URI + "经过过滤器前---");
		
		/**
		 * 一般做法
		 */
//		String exclude = config.getInitParameter("exclude");
//		if(null != exclude){
//			String[] arr = exclude.split(";");
//			for (int i = 0; i < arr.length; i++) {
//				if(arr[i] == null || "".equals(arr[i])) continue;
//				if(URI.indexOf(arr[i]) != -1){
//					chain.doFilter(request, response);
//					return;
//				}
//			}
//		}
		
		//设置字符集
		if (null != this.encoding && !this.encoding.equals("")) {
			request.setCharacterEncoding(this.encoding);
			response.setCharacterEncoding(this.encoding);
		} else {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
		}
		
		if (isExclude(req)) {
			flag = true;
		}else{
			Box box = new Box();
			loadCookie(req, box);
			String sessioncookieid = box.$cv(X.ENCRYPTED + X.SESSION_ID);
			String userid = box.$cv(X.ENCRYPTED + X.USERID);
			if(null == sessioncookieid || null == userid){
				flag =  false;
			}else{
				sessioncookieid = aes.decrypt(sessioncookieid);//aes.decrypt(box.$c(X.ENCRYPTED + X.SESSION_ID).getValue()); 
				userid = aes.decrypt(userid); //aes.decrypt(box.$c(X.ENCRYPTED + X.USERID).getValue()); 
				String sessionid = (String)req.getSession().getAttribute(X.SESSION_ID);
				User user = (User)req.getSession().getAttribute(X.USER);

				if (null != user && sessioncookieid.equals(sessionid) && userid.equals(user.getId())) {
					flag =  true;
				} else {
					flag =  false;
				}
			}
		}
		
		
		//CompressionResponseWrapper resWrapper = new CompressionResponseWrapper(res);
		
		if(flag){
//			if(isGzipSupported(req)){
//				//设置Content-Encoding实体报头，告诉浏览器实体正文采用了gzip压缩编码
//				resWrapper.setHeader("Content-Encoding", "gzip");
//				chain.doFilter(request, resWrapper);
//				GZIPOutputStream gzipos = resWrapper.getGZIPOutputStream();
//				gzipos.finish();//无需关闭流
//			}else{
//				chain.doFilter(request, resWrapper);
//			}
			
			//默认静态资源全部放行并添加版本号
			if(isStaticResource(req)){
				String requestURL = req.getRequestURL().toString();
				//String requestURI = req.getRequestURI();
				String queryStr = req.getQueryString();
				String newURL = null;
				if(StringUtils.isNotBlank(queryStr) && queryStr.trim().indexOf("V=") == -1){
	                newURL = requestURL + "?" + queryStr + "&V=" + new Date().getTime();
	                res.sendRedirect(newURL);
	                return;
	            }
	            if(StringUtils.isBlank(queryStr)){
	                newURL = requestURL + "?V=" + new Date().getTime();
	                res.sendRedirect(newURL);
	                return;
	            }
	            
	            chain.doFilter(request, response);
			}else{
				chain.doFilter(request, response);
			}
			
		}else{
			String reqType = req.getHeader("X-Requested-With");
			if(null != reqType && reqType.equalsIgnoreCase("XMLHttpRequest")) {
				//TODO 测试前端是否能拿到
				res.setHeader("sessionstatus","timeout");
				
				ResponseCondition rc = new ResponseCondition();
				rc.setHttpCode(10000);
				rc.setErrorMsg("登陆超时");
				rc.setIsSuccess(false);
				
				PrintWriter pw = null;
//				if(isGzipSupported(req)){
//					resWrapper.getWriter();    
//				}else{
//					pw = res.getWriter(); 
//				}
				pw = res.getWriter(); 
				pw.print(rc);//注意与write的区别    
		        pw.close();  //一般不需要手动关闭,servlet会自动关闭,这里由于不再继续后面的filter，所以可以手动关闭
			}
			else {
				//res.getRequestDispatcher("/login").forward(request, response);
				res.sendRedirect(req.getContextPath() + "/login");
			}
		}
		
		System.out.println("---" + URI + "经过过滤器后---");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		//获取web.xml中filter参数配置
		this.encoding = config.getInitParameter("encoding");  
		
		this.aes = new AES("2017.03_duyu_chengdu_china");
		
		this.config = config;
	}

	/**
	 * 检测浏览器是否支持GZIP
	 * @param req
	 * @return
	 */
	private boolean isGzipSupported(HttpServletRequest req) {
		String browserEncodings = req.getHeader("Accept-Encoding");
		return ((browserEncodings != null) && (browserEncodings.indexOf("gzip") != -1));
	}
	
	protected void loadCookie(HttpServletRequest request, Box box) {
		Cookie[] clientCookiesArray = request.getCookies();
		if (null == clientCookiesArray) {
			return;
		}
		for (Cookie c : clientCookiesArray) {
			box.getCookie().put(c.getName(), c);
		}
	}
	
	private boolean isExclude(final HttpServletRequest request) {
		if (excludeUrlPatterns == null) {
			return false;
		}
		
		//String servletPath = request.getServletPath();
		//String servletName = request.getServerName();
		//String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		
		for (String pattern : excludeUrlPatterns) {
			if (pathMatcher.match(pattern, uri)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isStaticResource(final HttpServletRequest request){
		String url = request.getRequestURL().toString();
		if(null != url && (url.endsWith(".js") || url.endsWith(".css"))){
			return true;
		}
		return false;
	}
}
