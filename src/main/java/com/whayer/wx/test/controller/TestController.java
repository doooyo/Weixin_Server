package com.whayer.wx.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whayer.wx.common.bean.BeanFactory;
import com.whayer.wx.common.bean.SpringFactory;

@Controller
public class TestController {

	private final static Logger log = LoggerFactory.getLogger(TestController.class);

	@RequestMapping("/index")
	@ResponseBody
	public String index() {
		log.info("TestController.index()");
		//TestBean bean = (TestBean)BeanFactory.getBean("testBean");
		TestBean bean = (TestBean)SpringFactory.getBean("testBean");
		bean.hello();
		return "test";
	}
}
