package com.ltchen.java.jvm;

/**
 * 
 * @file : StackSOF.java
 * @date : 2017年4月8日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : JVM栈内存溢出异常测试：java.lang.StackOverflowError
 * VM args: -verbose:gc -Xss128K
 * -verbose:gc 表示输出GC的详细信息
 * -Xss128k 设置每个线程的栈大小
 * 
 * 单线程请求栈深度大于虚拟机所允许的最大深度,栈内存溢出时异常信息为"java.lang.StackOverflowError",并会打印出内存溢出时的栈深度"
 */
public class StackSOF {

	private int stackLength = 1;
	
	public void stackLeak(){
		stackLength++;
		stackLeak();
	}
	public static void main(String[] args) {
		StackSOF stackSOF = new StackSOF();
		try {
			stackSOF.stackLeak();
		} catch (Throwable e) {
			System.out.println("stack length : " + stackSOF.stackLength);
			throw e;
		}

	}

}
