package com.whayer.wx.pay2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.whayer.wx.pay.util.CommonUtil;
import com.whayer.wx.pay.util.Constant;

public class Signature {
	private final static Logger log = LoggerFactory.getLogger(Signature.class);
	
	/**
     * 签名算法
     * @param o 要参与签名的数据对象
     * @return 签名
	 * @throws Exception 
     */
    public static String getSign(Object o) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        Class<? extends Object> cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
            	String name = f.getName();
            	XStreamAlias anno = f.getAnnotation(XStreamAlias.class);
            	if(anno != null)
            		name = anno.value();
                list.add(name + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        //字典序后再次加上key
        result += "key=" + Constant.APP_KEY;
        log.debug("签名数据前：" + result);
        result = CommonUtil.getMD5(result).toUpperCase();
        log.debug("签名数据后：" + result);
        return result;
    }

    public static String getSign(Map<String,Object> map) throws Exception{
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        //字典序后再次加上key
        result += "key=" + Constant.APP_KEY;
        log.debug("签名数据前：" + result);
        result = CommonUtil.getMD5(result).toUpperCase();
        log.debug("签名数据后：" + result);
        return result;
    }
}
