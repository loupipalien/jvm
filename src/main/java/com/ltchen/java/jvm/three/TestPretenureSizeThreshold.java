package com.ltchen.java.jvm.three;

/**
 * 
 * @file : TestPretenureSizeThreshold.java
 * @date : 2017年4月22日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 测试将大于3MB的对象直接放入老年代
 * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 * -verbose:gc 表示输出GC的详细信息
 * -Xms20M 设置JVM堆内存初始化大小为20M
 * -Xmx20M 设置JVM堆内存最大大小为20M
 * -Xmn10M 设置JVM堆内存最小大小为10M
 * -XX:+PrintGCDetails 打印GC回收的详细信息
 * -XX:SurvivorRatio=8 Edon和Survivor的比例是8：1：1.Survivor是有两个的
 * -XX:PretenureSizeThreshold=3145728 设置内存大小超过多大的对象直接放入老年代
 * 
 *  本次测试环境的JVM为1.8,新生代采用了Parallel Scavenge收集器,测试结果如下：
 *  Heap
 *  PSYoungGen      total 9216K, used 5243K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *  	eden space 8192K, 64% used [0x00000000ff600000,0x00000000ffb1ef60,0x00000000ffe00000)
 *  	from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 *  	to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 *  ParOldGen       total 10240K, used 0K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *  	object space 10240K, 0% used [0x00000000fec00000,0x00000000fec00000,0x00000000ff600000)
 *  Metaspace       used 2650K, capacity 4486K, committed 4864K, reserved 1056768K
 *  	class space    used 286K, capacity 386K, committed 512K, reserved 1048576K
 *  
 *  可以看到PretenureSizeThreshold对Parallel Scavenge收集器并不生效,仍然是将大于3MB的对象放入到了新生代中
 */
public class TestPretenureSizeThreshold {

	private static final int _1MB = 1024*1024;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		byte[] allocation;
		/*
		 * PretenureSizeThreshold参数只对Serial和ParNew两款收集器有效,Parallel Scavenge收集器不认识这个参数,也不需要设置
		 * 如果遇到必须使用此参数的场合,可以考虑ParNew加CMS的收集器组合
		 */
		allocation = new byte[4 * _1MB];
	}

}
