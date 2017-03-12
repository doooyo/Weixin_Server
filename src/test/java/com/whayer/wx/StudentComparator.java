package com.whayer.wx;

import java.io.Serializable;
import java.util.Comparator;

public class StudentComparator implements Comparator<Student>, Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4174607548541500455L;

	@Override
	public int compare(Student o1, Student o2) {
		//String类型默认实现了Comparable接口，所以有compareTo方法
		return o1.name.compareTo(o2.name);
	}

}
