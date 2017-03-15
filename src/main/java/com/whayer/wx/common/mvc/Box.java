package com.whayer.wx.common.mvc;

import java.io.Serializable;
import java.util.HashMap;

import javax.servlet.http.Cookie;

import com.whayer.wx.common.X;
import com.whayer.wx.common.json.Json;


public class Box implements Serializable{
	
	private static final long serialVersionUID = -6412916294910010217L;
	
	private HashMap<String, String> parameter;
	  private HashMap<String, Object> attribute;
	  private HashMap<String, Cookie> cookie;
	  

	  public Box() {
	    parameter = new HashMap<String, String>(10);
	    attribute = new HashMap<String, Object>(20);
	    cookie = new HashMap<String, Cookie>(10);
	  }

	  /**
	   * 将当前box 转化成 json
	   */
	  @Override
	  public String toString() {
	    return Json.toJson(this);
	  }

	  /**
	   * 将对象放入attribute中
	   * 
	   * @param key
	   * @param value
	   */
	  public void setAttribute(String key, Object value) {
	    attribute.put(key, value);
	  }

	  /**
	   * remove a cookie from client browser (by setting the maxage=0)
	   * 
	   * @param key
	   * @return
	   */
	  public boolean removeCookie(String key) {
	    Cookie c = cookie.get(key);
	    if (null == c) {
	      return false;
	    } 
	    c = new Cookie(c.getName(), "");
	    c.setMaxAge(0);
	    cookie.put(c.getName(), c);
	    return true;
	  }

	  /**
	   * 将cookie 添加到box中
	   * 
	   * @param key
	   * @param c
	   */
	  public void setCookie(String key, Cookie c) {
	    cookie.put(key, c);
	  }

	  /**
	   * 获取指定key的 attribute
	   * 
	   * @param key
	   * @return
	   */
	  public Object $a(String key) {
	    return attribute.get(key);
	  }

	  /**
	   * 获取指定key的 parameter
	   * 
	   * @param key
	   * @return
	   */
	  public String $p(String key) {
	    return parameter.get(key);
	  }

	  /**
	   * 获取指定key的cookie对象
	   * 
	   * @param key
	   * @return
	   */
	  public Cookie $c(String key) {
	    Cookie c = cookie.get(key);
	    if (null != c) {

	      return c;
	    } else {
	      // 未找到明文cookie 返回加密cookie
	      return cookie.get(X.ENCRYPTED + key);
	    }
	  }

	  /**
	   * 获取指定key的 cookie值
	   * 
	   * @param key
	   * @return
	   */
	  public String $cv(String key) {
	    Cookie c = cookie.get(key);
	    if (null != c) {
	      // 存在明文cookie 直接返回
	      return c.getValue();
	    } else {
	      c = cookie.get(X.ENCRYPTED + key);
	      if (null != c) {
	        return c.getValue();
	      } else {
	        return null;
	      }
	    }
	  }

	  // --------------------- Getter & Setter ------------------------

	  public HashMap<String, String> getParameter() {
	    return parameter;
	  }

	  public void setParameter(HashMap<String, String> parameter) {
	    this.parameter = parameter;
	  }

	  public HashMap<String, Object> getAttribute() {
	    return attribute;
	  }

	  public void setAttribute(HashMap<String, Object> attribute) {
	    this.attribute = attribute;
	  }

	  public HashMap<String, Cookie> getCookie() {
	    return cookie;
	  }

	  public void setCookie(HashMap<String, Cookie> cookie) {
	    this.cookie = cookie;
	  }

}
