package com.ltchen.java.jvm;

/**
 * 
 * @file : StackOOM.java
 * @date : 2017年4月8日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : JVM栈内存溢出异常测试：java.lang.OutOfMemoryError
 * VM args: -verbose:gc -Xss2M
 * -verbose:gc 表示输出GC的详细信息
 * -Xss2M 设置每个线程的栈大小
 * 
 * 创建多线程动态扩展栈个数时内存不足,栈内存溢出时异常信息为"java.lang.OutOfMemoryError"
 * 此异常暂时未能生成抛出,因为栈的个数与栈本身大小和进程所能占用的内存大小有关,而进程所能占用的内存大小由机器操作系统控制
 * 当栈内存抛出此错误时可以尝试通过减小每个栈的内存大小或减小堆内存的大小来避免栈动态扩展时内存不足
 */
public class StackOOM {

	private int threadNum = 1;
	
	private void dontStop(){
		while(true){}
	}
	
	public void stackLeakByThread(){
		while(true){
			Thread thread = new Thread(new Runnable(){
				@Override
				public void run(){
					dontStop();
				}
			});
			thread.start();
			System.out.println("the number of thread is : " + threadNum++);
		}
	}
	
	public static void main(String[] args) {
		StackOOM stackOOM = new StackOOM();
		stackOOM.stackLeakByThread();
	}

}
