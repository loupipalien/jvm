package com.ltchen.java.jvm;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * 
 * @file : DirectMemoryOOM.java
 * @date : 2017年4月8日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : JVM本机直接内存溢出异常测试：java.lang.OutOfMemoryError
 * VM args: -verbose:gc -Xmx20M -XX:MaxDirectMemorySize=10M
 * -verbose:gc 表示输出GC的详细信息
 * -Xmx20M 设置表示JVM堆内存最大大小为20M
 * -XX:MaxDirectMemorySize=10M 设置本机直接内存最大值大小
 * 
 * 由于DirectMemory导致的内存溢出，一个明显的特征是在Heap Dump文件中不会看见明显的异常
 */
@SuppressWarnings("restriction")
public class DirectMemoryOOM {

	private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) {
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		try {
			Unsafe unsafe = (Unsafe) unsafeField.get(null);
			while(true){
				unsafe.allocateMemory(_1MB);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
