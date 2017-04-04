package com.whayer.wx.common.page;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class HtmlParser {

	  public HtmlParser() {
	  }

	  public static LinkedList<String> parseHtml(String docString, String cssQuery) {
	    LinkedList<String> result = null;
	    Document doc = Jsoup.parse(docString);
	    Elements es = doc.select(cssQuery);
	    if (!es.isEmpty()) {
	      result = new LinkedList<String>();
	      for (int i = 0; i < es.size(); i++) {
	        Element e = es.get(i);
	        result.addLast(e.html());
	      }

	    }
	    return result;
	  }

	  public static LinkedList<String> parseText(String docString, String cssQuery) {
	    LinkedList<String> result = null;
	    Document doc = Jsoup.parse(docString);
	    Elements es = doc.select(cssQuery);
	    if (!es.isEmpty()) {
	      result = new LinkedList<String>();
	      for (int i = 0; i < es.size(); i++) {
	        Element e = es.get(i);
	        result.addLast(e.text());
	      }

	    }
	    return result;
	  }

	  public static LinkedList<String> parseAttribute(String docString, String cssQuery) {
	    LinkedList<String> result = null;
	    Document doc = Jsoup.parse(docString);
	    String attribute = cssQuery.split("\\.")[1];
	    cssQuery = cssQuery.split("\\.")[0];
	    Elements es = doc.select(cssQuery);
	    if (!es.isEmpty()) {
	      result = new LinkedList<String>();
	      for (int i = 0; i < es.size(); i++) {
	        Element e = es.get(i);
	        result.addLast(e.attr(attribute));
	      }
	    }
	    return result;
	  }

	  public static LinkedList<String> getSubUrl(String docString, String currentUrl) {
	    LinkedList<String> result = null;
	    Document doc = Jsoup.parse(docString);
	    Elements es = doc.select("a[href]:has(img)");
	    if (!es.isEmpty()) {
	      result = new LinkedList<String>();
	      for (int i = 0; i < es.size(); i++) {
	        Element e = es.get(i);
	        String subUrl = e.attr("href");
	        if (isSub(currentUrl, subUrl))
	          result.add(subUrl);
	      }
	    }
	    return result;
	  }

	  private static boolean isSub(String url, String subUrl) {
	    if (!subUrl.contains("://"))
	      return !"./".equals(subUrl) && !"/".equals(subUrl);
	    URL u2;
	    try {
	      u2 = new URL(subUrl);
	      return url.contains(u2.getHost());
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	      return false;
	    }

	  }
	  
	  public static String GetClientIp(HttpServletRequest request) {
		  String ip = request.getHeader("x-forwarded-for");
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		      ip = request.getHeader("Proxy-Client-IP");
		    }
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		      ip = request.getHeader("WL-Proxy-Client-IP");
		    }
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		      ip = request.getRemoteAddr();
		    }
		    return ip;
	  }
	}
