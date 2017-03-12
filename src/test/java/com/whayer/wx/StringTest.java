package com.whayer.wx;

public class StringTest {
	public static void main(String[] args) {
		String s1="abc";//创建String型引用变量s1，指向字符串常量池中的"abc"字符串。  
        String s2=new String("abc");//栈中创建s2引用变量，堆内存中创建"abc"字符串对象，并被s2指向。  
        String s3="a"+"bc";//"a"和"bc"合成为字符串常量"abc",先在字符串常量池中查找是否已有"abc"字符串对象，已有，不再重新创建对象，s3和s1指向的是字符串常量池中的同一个对象，s1==s3.  
        String s4="a"+new String("bc");//在堆中创建了"bc"对象，与"a"相加，返回新的字符串对象赋给s4,s4还指向堆中的字符串对象。  
        System.out.println(s1==s2);//false  
        System.out.println(s1==s3);//true  
        System.out.println(s1==s4);//false  
        System.out.println(s2==s4);//false  
        System.out.println(s1.equals(s2));//true  
        System.out.println("=============================");  
        String s5=new String("def");//实际创建了2个对象，编译完成后字符串常量池中就有了"def"，运行时在堆内存中创建String类对象"def".  
        String s6="def";  
        System.out.println(s5==s6);//false, s5指向堆内存中的"def"对象，s6是指向字符串常量池中的"def"字符串对象。  
        System.out.println(s6==s5.intern());//true, s5.intern()指向字符串常量池中的"def"字符串对象。          
	}
}
