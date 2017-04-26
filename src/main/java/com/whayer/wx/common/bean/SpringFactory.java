package com.whayer.wx.common.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

/**
 * 获取bean实例 避免使用(new File)
 * 
 * @author duyu
 * 
 */
public class SpringFactory implements ApplicationContextAware {

	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext cxt) throws BeansException {
		context = cxt;
		System.out.println(context.toString());
	}

	public <T> T getBean(String beanName, Class<T> clazs) {
		return clazs.cast(getBean(beanName));
	}

	public Object getBean(String beanName) {
		Object object = null;
		object = context.getBean(beanName);
		return object;
	}

	/**
	 * 
	 * @param url
	 *        classpath:test/1.txt
	 *        file:/Users/duyu/Desktop/test.txt
	 *        url:http://blog.csdn.net/hss01248/article/details/53405251
	 * @return
	 */
	public Resource getResource(String url){
		if(null == url || "".equals(url)) 
			return null;
		Resource resource = context.getResource(url);
		return resource;
	}

}
