package com.whayer.wx.common.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Json {
	private final static Gson gson     = new GsonBuilder().serializeNulls().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	  private final static Gson gsonPlain = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	  private final static Gson gson2Null =  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 

	  public static String toJson(Object o) {
	    return gson.toJson(o);
	  }
	  
	  /**
	   * 当结果为null时，不返回键值对
	   * @param o
	   * @return
	   */
	  public static String toJson2Null(Object o){
	    return gson2Null.toJson(o);
	  }

	  /**
	   * 返回未格式化的json
	   * @param o
	   * @return
	   */
	  public static String toJsonPlain(Object o) {
	    return gsonPlain.toJson(o);
	  }

	  public static Object fromJson(String json, Class<? extends Object> c) {
	    return gson.fromJson(json, c);
	  }

	  public static Object fromJson(String json, Type t) {
	    return gson.fromJson(json, t);
	  }
}
