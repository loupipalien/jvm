package com.ltchen.java.jvm.two;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @file : RunTimeConstantPoolOOM.java
 * @date : 2017年4月8日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : JVM方法区(非堆)内存溢出测试：java.lang.OutOfMemoryError
 * JDK6(运行时常量池分配在永久代内,永久代是HotSpot中方法区(非堆)的实现) VM args: -verbose:gc -XX:PermSize=10M -XX:MaxPermSize=10M
 * JDK8(运行时常量池放在堆中) VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M [-XX:-UseGCOverheadLimit]
 * -verbose:gc 表示输出GC的详细信息
 * -XX:PermSize=10M 设置永久代内存初始值大小
 * -XX:MaxPermSize=10M 设置永久代内存最大值大小
 * -Xms20M 设置JVM堆内存初始化大小为20M
 * -Xmx20M 设置JVM堆内存最大大小为20M
 * -Xmn10M 设置JVM堆内存最小大小为10M
 * 
 * JDK6:运行时常量池放在非堆中,运行报错：java.lang.OutOfMemoryError: PermGen space
 * JDK8:运行时常量池放在堆中,运行报错:java.lang.OutOfMemoryError: GC overhead limit exceeded
 * JDK8,并且使用-XX:-UseGCOverheadLimit参数:运行时常量池放在堆中,运行报错:java.lang.OutOfMemoryError: Java heap space
 */
public class RunTimeConstantPoolOOM {

	public static void main(String[] args) {
		//使用List维持常量池的引用，避免Full GC回收常量池行为
		List<String> list = new ArrayList<String>();
		int i = 0;
		while(true){
			/*
			 * intern()是一个native方法，作用是：如果字符串常量池中已经包含了一个等于此String对象的字符串,
			 * 则返回代表池中这个字符串的String对象的引用;否则,将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用
			 */
			list.add(String.valueOf(i++).intern());
		}
	}

}
