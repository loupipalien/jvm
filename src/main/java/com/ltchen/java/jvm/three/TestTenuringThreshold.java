package com.ltchen.java.jvm.three;

/**
 * 
 * @file : TestTenuringThreshold.java
 * @date : 2017年4月22日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 测试在survivor区存活一定"年龄"的对象会放入老年代
 * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=[1|15] -XX:+PrintTenuringDistribution
 * -verbose:gc 表示输出GC的详细信息
 * -Xms20M 设置JVM堆内存初始化大小为20M
 * -Xmx20M 设置JVM堆内存最大大小为20M
 * -Xmn10M 设置JVM堆内存最小大小为10M
 * -XX:+PrintGCDetails 打印GC回收的详细信息
 * -XX:SurvivorRatio=8 Edon和Survivor的比例是8：1：1.Survivor是有两个的
 * -XX:MaxTenuringThreshold=[1|15] 设置对象年龄多大时从新生代的survivor区放入老年代
 * -XX:+PrintTenuringDistribution 打印对象年龄分布信息
 * 对象年龄：在eden区出生并经过第一次MinorGC后仍存活,并且能被survivor区容纳,将对象移动到survivor区并设置对象年龄为1,
 * 			当对象的年龄到达一定程度(默认15岁),就会被晋升到老年代中。对于晋升的年龄阈值靠MaxTenuringThreshold设置
 * 
 * 本次测试环境的JVM为1.8,新生代采用了Parallel Scavenge收集器,测试结果如下：
 * Heap
 * 	PSYoungGen      total 9216K, used 5499K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 * 		eden space 8192K, 67% used [0x00000000ff600000,0x00000000ffb5efb0,0x00000000ffe00000)
 *  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 *  	to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 *  ParOldGen       total 10240K, used 8192K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 *  	object space 10240K, 80% used [0x00000000fec00000,0x00000000ff400020,0x00000000ff600000)
 *  Metaspace       used 2650K, capacity 4486K, committed 4864K, reserved 1056768K
 *  	class space    used 286K, capacity 386K, committed 512K, reserved 1048576K
 *  
 *  测试结果可以看到MaxTenuringThreshold在Parallel Scavenge收集器无论设置为1还是设置为15结果都相同,
 *  可以认为在Parallel Scavenge收集器下,新生代容纳不下新对象时直接放入老年代,新生代中并不发生GC
 */
public class TestTenuringThreshold {

	private static final int _1MB = 1024*1024;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		byte[] allocation1, allocation2, allocation3;
		allocation1 = new byte[_1MB /4];
		allocation2 = new byte[4 * _1MB];
		/*
		 * 当新生代收集器为Serial收集器时,发生第一次MinorGC,allocation1会被放入survivor区,
		 * 由于阈值年龄设置为1,则会再移动到老年代中,survivor区占用为0
		 */
		allocation3 = new byte[4 * _1MB];
		allocation3 = null;
		/*
		 * 当阈值年龄设置为15时,发生第二次MinorGC,allocation1仍然再survivor区中
		 */
		allocation3 = new byte[4 * _1MB];
		
	}

}
