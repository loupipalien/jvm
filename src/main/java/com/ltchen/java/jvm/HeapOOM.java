package com.ltchen.java.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @file : HeapOOM.java
 * @date : 2017年4月7日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : JVM堆内存溢出异常测试：java.lang.OutOfMemoryError
 * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * -verbose:gc 表示输出GC的详细信息
 * -Xms20M 设置JVM堆内存初始化大小为20M
 * -Xmx20M 设置JVM堆内存最大大小为20M
 * -Xmn10M 设置JVM堆内存最小大小为10M
 * -XX:+PrintGCDetails 打印GC回收的详细信息
 * -XX:SurvivorRatio=8 Edon和Survivor的比例是8：1：1.Survivor是有两个的
 * 
 * 堆溢出时异常信息为"java.lang.OutOfMemoryError",会进一步提示"Java heap space"
 */
public class HeapOOM {

	static class OOMObject{}
	
	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<OOMObject>();
		
		while(true){
			list.add(new OOMObject());
		}
	}

}
