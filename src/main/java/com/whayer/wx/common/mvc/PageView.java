package com.whayer.wx.common.mvc;

import java.util.Date;

/**
 * 页面信息
 * @author duyu
 *
 */
public final class PageView {
	// 请求地址
	  private String url;
	  // 请求时间
	  private Date   date;
	  // 客户端
	  private String ip;
	  // 客户端OS
	  private String os;
	  // 客户端浏览器
	  private String browser;
	  // 客户端 agent信息
	  private String agent;
	  // HTTP REFERER信息
	  private String referer;
	  // 登陆用户id
	  private String userId;
	  // 用户地址信息
	  private String userLocation;

	  public String getUrl() {
	    return url;
	  }

	  public void setUrl(String url) {
	    this.url = url;
	  }

	  public Date getDate() {
	    return date;
	  }

	  public void setDate(Date date) {
	    this.date = date;
	  }

	  public String getIp() {
	    return ip;
	  }

	  public void setIp(String ip) {
	    this.ip = ip;
	  }

	  public String getOs() {
	    return os;
	  }

	  public void setOs(String os) {
	    this.os = os;
	  }

	  public String getBrowser() {
	    return browser;
	  }

	  public void setBrowser(String browser) {
	    this.browser = browser;
	  }

	  public String getAgent() {
	    return agent;
	  }

	  public void setAgent(String agent) {
	    this.agent = agent;
	  }

	  public String getReferer() {
	    return referer;
	  }

	  public void setReferer(String referer) {
	    this.referer = referer;
	  }

	  public String getUserId() {
	    return userId;
	  }

	  public void setUserId(String userId) {
	    this.userId = userId;
	  }

	  public String getUserLocation() {
	    return userLocation;
	  }

	  public void setUserLocation(String userLocation) {
	    this.userLocation = userLocation;
	  }
}
