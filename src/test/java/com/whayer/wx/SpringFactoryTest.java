package com.whayer.wx;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.whayer.wx.common.bean.SpringFactory;
import com.whayer.wx.controller.TestBean;

public class SpringFactoryTest {
	
	//private static ApplicationContext ctx;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//ctx = new FileSystemXmlApplicationContext("classpath:IOC.xml");
	}

	@Test
	public void test() {
		TestBean bean = (TestBean)SpringFactory.getBean("testBean");
		//TestBean bean = (TestBean)ctx.getBean("testBean");
		bean.hello();
	}

}
