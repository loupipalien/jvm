package com.ltchen.java.jvm.three;

/**
 * 
 * @file : ReferenceCountingGC.java
 * @date : 2017年4月22日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 使用循环引用测试JVM是否GC时使用引用计数算法
 * VM args: -verbose:gc -XX:+PrintGCDetails [-Xms20M -Xmx20M -Xmn10M]
 * -verbose:gc 表示输出GC的详细信息
 * -XX:+PrintGCDetails 打印GC回收的详细信息
 * -Xms20M 设置JVM堆内存初始化大小为20M
 * -Xmx20M 设置JVM堆内存最大大小为20M
 * -Xmn10M 设置JVM堆内存最小大小为10M
 * 
 * 测试数据为：[GC (System.gc()) [PSYoungGen: 5079K->648K(9216K)] 5079K->656K(19456K), 0.0009027 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
 * 发生GC时PSYoungGen从5079K-648K,意味着明显发生了内存清理,即JVM垃圾回收时并不是采用引用计数算法
 */
public class ReferenceCountingGC {

	private Object instance = null;
	private static final int _1MB = 1024*1024;
	//这个成员属性唯一的意义只是占些内存，以便在GC日志中看清楚是否被回收过
	@SuppressWarnings("unused")
	private byte[] bigSize = new byte[2 * _1MB];
	
	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

	public static void main(String[] args) {
		ReferenceCountingGC objA = new ReferenceCountingGC();
		ReferenceCountingGC objB = new ReferenceCountingGC();
		objA.setInstance(objB);
		objB.setInstance(objA);
		
		objA = null;
		objB = null;
		
		//假设在这行发生GC,objA和objB是否能被收回
		System.gc();
	}
	
}
