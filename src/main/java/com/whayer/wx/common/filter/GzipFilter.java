package com.whayer.wx.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Gzip Filter
 * @author duyu
 *
 */
public class GzipFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		
		if (req instanceof HttpServletRequest) {
			
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			
			String URI = request.getRequestURI();
			
			System.out.println("---" + URI + "经过过GZIP滤器前---");
			
			if (isGZIPSupported(request)) {
				GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);
				chain.doFilter(req, wrappedResponse);
				wrappedResponse.finishResponse();
				System.out.println("---" + URI + "经过过GZIP滤器后---");
				return;
			}
			chain.doFilter(req, res);
			
		}

	}
	
	private boolean isGZIPSupported(HttpServletRequest req) {
		String browserEncodings = req.getHeader("Accept-Encoding");
		return ((browserEncodings != null) && (browserEncodings.indexOf("gzip") != -1));
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
