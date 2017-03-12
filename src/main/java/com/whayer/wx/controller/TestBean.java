package com.whayer.wx.controller;

import org.springframework.stereotype.Repository;

//必须使用注解才可以找到
@Repository
public class TestBean {
	private String name;
	private Integer age;
	
	@Override
	public String toString() {
		//Object对象的toString()默认返回的是对象的内存地址：Clazz@address
		//若需要打印具体属性值信息需要自己重写
		return "TestBean [name=" + name + ", age=" + age + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)  //首先判断是否引用相等，相等说明就是同一个对象，肯定相等
			return true;
		if (obj == null)  //和null肯定不相等，直接返回false
			return false;
		if (getClass() != obj.getClass()) //类对象(关心的是类本身描述的信息-key，类的对象关心的是对象的值信息-value) Type若不等，肯定就不相等 
			return false;
		TestBean other = (TestBean) obj;
		//然后再比较属性值是否一一相等
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void hello(){
		System.out.println("hello world!");
	}
}
