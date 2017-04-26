package com.whayer.wx;


import org.junit.BeforeClass;
import org.junit.Test;

import com.whayer.wx.base.UnitTestBase;
import com.whayer.wx.common.bean.SpringFactory;
import com.whayer.wx.test.controller.TestBean;

public class SpringFactoryTest extends UnitTestBase{
	
	public SpringFactoryTest() {
		super("classpath:IOC.xml");
	}
	
	//private static ApplicationContext ctx;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//ctx = new FileSystemXmlApplicationContext("classpath:IOC.xml");
	}

	@Test
	public void test() {
		SpringFactory springFactory = super.getBean("springFactory");
		TestBean bean = (TestBean)springFactory.getBean("testBean");
		//TestBean bean = (TestBean)ctx.getBean("testBean");
		bean.hello();
	}

}
