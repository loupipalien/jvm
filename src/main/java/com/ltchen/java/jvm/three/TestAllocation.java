package com.ltchen.java.jvm.three;

/**
 * 
 * @file : TestAllocation.java
 * @date : 2017年4月22日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 测试内存空间分配策略
 * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * -verbose:gc 表示输出GC的详细信息
 * -Xms20M 设置JVM堆内存初始化大小为20M
 * -Xmx20M 设置JVM堆内存最大大小为20M
 * -Xmn10M 设置JVM堆内存最小大小为10M
 * -XX:+PrintGCDetails 打印GC回收的详细信息
 * -XX:SurvivorRatio=8 Edon和Survivor的比例是8：1：1.Survivor是有两个的
 * 
 * 本次测试环境的JVM为1.8,新生代采用了Parallel Scavenge收集器,测试结果如下：
 *Heap
 *	PSYoungGen      total 9216K, used 7292K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 *		eden space 8192K, 89% used [0x00000000ff600000,0x00000000ffd1f018,0x00000000ffe00000)
 *		from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 * 		to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 * 	ParOldGen       total 10240K, used 4096K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 * 		object space 10240K, 40% used [0x00000000fec00000,0x00000000ff000010,0x00000000ff600000)
 * 	Metaspace       used 2650K, capacity 4486K, committed 4864K, reserved 1056768K
 *  	class space    used 286K, capacity 386K, committed 512K, reserved 1048576K 
 *
 *可以看到并没有发生GC,在分配内存时allocation1,allocation2,allocation3分配在了新生代中,allocation4直接分配在了老年代中
 */
public class TestAllocation {

	private static final int _1MB = 1024*1024;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		byte[] allocation1,allocation2,allocation3,allocation4;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		allocation1 = null;
		/*
		 * 当新生代采用Serial收集器时,这里会发生一次新生代GC(Minor GC)
		 * 由于eden区已经被占用了6MB,而survivor区空间只有1MB,不足以放下allocation1,allocation2,allocation3任意一个对象
		 * 所以会通过分配担保机制直接转移到老年代中去,将allocation4放入新生代的eden区
		 */
		allocation4 = new byte[3 * _1MB];
	}

}
