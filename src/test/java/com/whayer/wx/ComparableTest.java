package com.whayer.wx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;



public class ComparableTest {
	public static void main(String[] args) {
		List<Student> list = new ArrayList<Student>();
		Random random = new Random();
		list.add(new Student(random.nextInt(1000) +"", "leo"));
		list.add(new Student(random.nextInt(1000) +"", "tom"));
		list.add(new Student(random.nextInt(1000) +"", "jerry"));
		list.add(new Student(random.nextInt(1000) +"", "doyo"));
		list.add(new Student(10000 +"", "jack"));
		System.out.println("--------排序前--------");
		for(Student stu : list){
			System.out.println("student: " + stu.id + "/" + stu.name);
		}
		System.out.println("--------排序后--------");
		Collections.sort(list);
		
		for(Student stu : list){
			System.out.println("student: " + stu.id + "/" + stu.name);
		}
		
		System.out.println("--------名字排序--------");
		Collections.sort(list, new StudentComparator());
		for(Student stu : list){
			System.out.println("student: " + stu.id + "/" + stu.name);
		}
	}
}
