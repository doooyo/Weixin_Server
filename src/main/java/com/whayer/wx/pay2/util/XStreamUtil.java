package com.whayer.wx.pay2.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class XStreamUtil {
	/**
	 * 对象转XML字符串
	 * @param obj
	 * @return
	 */
	public static String Obj2Xml(Object obj){
		//解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStreamForRequestPostData.alias("xml", obj.getClass());
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String xml = xStreamForRequestPostData.toXML(obj);
        return xml;
	}
	
	/**
	 * XML字符串转Map
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> Xml2Map(String xml) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList)
            map.put(e.getName(), e.getText());
        return map;
    }
	
	/**
	 * XML字符串转对象
	 * @param xml
	 * @param klass
	 * @return
	 */
	public static Object Xml2Obj(String xml, Class<?> klass){
		XStream xStream = new XStream();
		xStream.alias("xml", klass.getClass()); 
		return xStream.fromXML(xml);
	}
}
