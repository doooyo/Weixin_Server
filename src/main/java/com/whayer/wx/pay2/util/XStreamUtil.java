package com.whayer.wx.pay2.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStream.alias("xml", obj.getClass());
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String xml = xStream.toXML(obj);
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
	 * 将请求流转化为Map
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> Xml2Map(HttpServletRequest req) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        InputStream ins = req.getInputStream();
        Document doc = reader.read(ins);
        Element root = doc.getRootElement();
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
	
	public static void main(String[] args) {
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>" +
						"<return_msg><![CDATA[OK]]></return_msg>" +
						"<appid><![CDATA[wx2e4ffffd52348193]]></appid>" +
						"<mch_id><![CDATA[1465571902]]></mch_id>" +
						"<device_info><![CDATA[WEB]]></device_info>" +
						"<nonce_str><![CDATA[cACljoSPpvw1nTpR]]></nonce_str>" +
						"<sign><![CDATA[97FC87D8EDBC6D2C9201510A119AD4AC]]></sign>" +
						"<result_code><![CDATA[SUCCESS]]></result_code>" +
						"<prepay_id><![CDATA[wx20170511002206828dfd536c0124395974]]></prepay_id>" +
						"<trade_type><![CDATA[JSAPI]]></trade_type>" +
						"</xml>";
		
		try {
			Map<String, String> map = XStreamUtil.Xml2Map(xml);
			System.out.println(map.toString());
//			OrderReturnInfo returnInfo = (OrderReturnInfo)XStreamUtil.Xml2Obj(xml, OrderReturnInfo.class);
//			System.out.println(returnInfo.getPrepay_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
