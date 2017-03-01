package com.whayer.wx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	private final static Logger log = LoggerFactory.getLogger(TestController.class);

	@RequestMapping("/index")
	@ResponseBody
	public String index() {
		log.info("TestController.index()");
		return "test controller";
	}
}