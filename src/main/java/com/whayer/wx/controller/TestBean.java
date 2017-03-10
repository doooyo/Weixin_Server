package com.whayer.wx.controller;

import org.springframework.stereotype.Repository;

//必须使用注解才可以找到
@Repository
public class TestBean {
	public void hello(){
		System.out.println("hello world!");
	}
}
