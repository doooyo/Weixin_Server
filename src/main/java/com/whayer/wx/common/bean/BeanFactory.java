package com.whayer.wx.common.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryAware;

public class BeanFactory implements BeanFactoryAware{
	private static org.springframework.beans.factory.BeanFactory beanFactory;  
    
    public static Object getBean(String beanName) {  
         return beanFactory.getBean(beanName);  
    }  
      
    public static <T> T getBean(String beanName, Class<T> clazs) {  
         return clazs.cast(getBean(beanName));  
    }  

	@Override
	public void setBeanFactory(org.springframework.beans.factory.BeanFactory beanFactory) throws BeansException {
		BeanFactory.beanFactory = beanFactory; 
		
	}  
}
