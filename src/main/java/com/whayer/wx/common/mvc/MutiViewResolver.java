package com.whayer.wx.common.mvc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * Copyright (C) 2015 GO2.CN. All rights reserved. This computer program source
 * code file is protected by copyright law and international treaties.
 * Unauthorized distribution of source code files, programs, or portion of the
 * package, may result in severe civil and criminal penalties, and will be
 * prosecuted to the maximum extent under the law.
 * 
 * SpringMVC 多视图支持
 * 
 * @author duyu
 * @since 2017-02-26
 */
public class MutiViewResolver implements ViewResolver {
	private static final Logger log = LoggerFactory.getLogger(ViewResolver.class);
	private HashMap<String, ViewResolver> resolvers;

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		log.debug("@@@@Resolving view : " + viewName);
		ViewResolver vr;
		if (viewName.startsWith("forward:") || viewName.startsWith("redirect:")) {
			vr = resolvers.get("jsp");
		} else {
			if (viewName.indexOf(".") == -1) {
				throw new Exception(
						"View name must contains at least one '.' such as index/main.jsp,fetch/f.html,fetch/f.ftl");
			}
			String suffix = viewName.substring(viewName.lastIndexOf(".") + 1);
			vr = resolvers.get(suffix);
			if (vr == null) {
				throw new Exception("No ViewResolver with suffix <" + suffix + "> is configured in spring IOC");
			}
		}
		return vr.resolveViewName(viewName, locale);
	}

	/**
	 * spring 多视图,多key支持, 用于解析 逗号分隔的key,使他们映射到同一个视图解析器上 .
	 */
	private void analyse() {
		if (null != resolvers) {
			Iterator<String> ki = resolvers.keySet().iterator();
			String keys;
			while (ki.hasNext()) {
				keys = ki.next();
				if (!keys.contains(",")) {
					continue;
				}
				for (String k : keys.split(",")) {
					resolvers.put(k, resolvers.get(keys));
				}
			}
		}
	}

	// --------------------- Getter & Setter ------------------------

	public HashMap<String, ViewResolver> getResolvers() {
		return resolvers;
	}

	/**
	 * after set analyes the key
	 * 
	 * @param resolvers
	 */
	public void setResolvers(HashMap<String, ViewResolver> resolvers) {
		this.resolvers = resolvers;
		analyse();
	}
}
