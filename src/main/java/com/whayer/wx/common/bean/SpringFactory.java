package com.whayer.wx.common.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取bean实例 避免使用(new File)
 * 
 * @author duyu
 * 
 */
public class SpringFactory implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext cxt) throws BeansException {
		context = cxt;
		System.out.println(context.toString());
	}

	public static <T> T getBean(String beanName, Class<T> clazs) {
		return clazs.cast(getBean(beanName));
	}

	public static Object getBean(String beanName) {
		Object object = null;
		object = context.getBean(beanName);
		return object;
	}

}
