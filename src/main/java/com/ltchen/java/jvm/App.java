package com.ltchen.java.jvm;

/**
 * 
 * @file : App.java
 * @date : 2017年4月8日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : tmp test class
 */
public class App {
    public static void main( String[] args ){
//    	String str = args[0];
//        System.out.println(str.intern() == str);
        
		String str1 = new StringBuilder("计算机").append("软件").toString();
		System.out.println(str1.intern() == str1);
		
		String str2 = new StringBuilder("ja").append("va").toString();
		System.out.println(str2.intern() == str2);
		
		String str3 = new StringBuilder("计算机").append("软件").toString();
		System.out.println(str3.intern() == str3);
    }
}
