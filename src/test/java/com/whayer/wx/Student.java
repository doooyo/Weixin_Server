package com.whayer.wx;

public class Student implements Comparable<Student> {
	public String id;
	public String name;
	
	public Student(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * 重写toString，否则返回clazz&address(Student&01x0)
	 */
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}

	/**
	 * 若对象Student需要当作hash类型集合的key值，则必须重写hashCode，同时equals也得同时重写
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	/**
	 * 1. 先比较地址 2.与null比较 3.比较类型 4.最后比较对象的每个属性值
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	/***
	 * 继承Comparable接口必须实现此方法(此接口也只有一个方法)
	 * 返回整数表明大，返回负数表明小，返回0表明相等
	 */
	@Override
	public int compareTo(Student o) {
		return this.id.compareTo(o.id);
	}
	
	
}
