package com.whayer.wx.common.bean;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;

import com.whayer.wx.common.X;

public class BeanKit {
	//private static Logger log = LoggerFactory.getLogger(BeanKit.class);
	private static final String SET = "set";

	/**
	 * Fill the filed of target by the source source can be a Map
	 * <String,Object> or ServletRequest
	 * 
	 * @param target
	 * @param source
	 */
	public static void fill(Object target, Object source) {
		// get all method of target
		Method[] ms = target.getClass().getMethods();
		String key;
		for (Method m : ms) {
			if (m.getName().startsWith(SET) && m.getParameterTypes().length == 1) {
				// method is a setter
				key = m.getName().substring(3);
				// get the filed name
				key = key.substring(0, 1).toLowerCase() + key.substring(1);
				Object value = getValue(key, source);
				setValue(m, value, target);

			}
		}
	}

	/**
	 * set the value to target by method m using reflection automatic type parse
	 * for String>Date String>Integer String>Long String>Double
	 * 
	 * @param m
	 * @param value
	 * @param target
	 */
	private static void setValue(Method m, Object value, Object target) {
		if (value == null) {
			return;
		}
		Object v = null;
		// Parsing the data type
		if (value.getClass().equals(m.getParameterTypes()[0])) {
			v = value;
		} else if (value instanceof String) {
			if (Date.class.equals(m.getParameterTypes()[0])) {
				v = X.string2date((String) value, "time");
			} else if (Integer.class.equals(m.getParameterTypes()[0])) {
				v = Integer.parseInt((String) value);
			} else if (Long.class.equals(m.getParameterTypes()[0])) {
				v = Long.parseLong((String) value);
			} else if (Double.class.equals(m.getParameterTypes()[0])) {
				v = Double.parseDouble((String) value);
			}
		}
		if (v != null) {
			try {
				// invoke the setter
				m.invoke(target, v);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * get the value of specified key from Map<String,Object> or
	 * ServletRequest(parameter)
	 * 
	 * @param key
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Object getValue(String key, Object source) {
		Object value = null;
		if (source instanceof Map<?, ?>) {
			value = ((Map<String, Object>) source).get(key);
		} else if (source instanceof ServletRequest) {
			value = ((ServletRequest) source).getParameter(key);
		}
		return value;
	}
}
