package com.whayer.wx.spring;

import java.io.IOException;

import org.junit.Test;

import com.whayer.wx.base.UnitTestBase;
import com.whayer.wx.test.spring.SpringResource;

public class SpringResourceTest extends UnitTestBase{
	
	public SpringResourceTest() {
		super("classpath:test/spring-resource.xml");
	}
	
	@Test
	public void testResource() {
		SpringResource resource = super.getBean("springResource");
		try {
			resource.getResource();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
