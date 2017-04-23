package com.ltchen.java.jvm.three;

/**
 * 
 * @file : TestDynamicTenuringThreshold.java
 * @date : 2017年4月22日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 测试在survivor区存活相同年龄所有对象大小的总和大于survivor区的一半,年龄大于或等于该年龄的对象可直接移动的老年代,无须达到设置的年龄阈值
 * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution
 * -verbose:gc 表示输出GC的详细信息
 * -Xms20M 设置JVM堆内存初始化大小为20M
 * -Xmx20M 设置JVM堆内存最大大小为20M
 * -Xmn10M 设置JVM堆内存最小大小为10M
 * -XX:+PrintGCDetails 打印GC回收的详细信息
 * -XX:SurvivorRatio=8 Edon和Survivor的比例是8：1：1.Survivor是有两个的
 * -XX:MaxTenuringThreshold=[1|15] 设置对象年龄多大时从新生代的survivor区放入老年代
 * -XX:+PrintTenuringDistribution 打印对象年龄分布信息
 * 
 * 本次测试环境的JVM为1.8,新生代采用了Parallel Scavenge收集器,测试结果如下：
 * Heap
 * 	PSYoungGen      total 9216K, used 5755K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
 * 		eden space 8192K, 70% used [0x00000000ff600000,0x00000000ffb9ef90,0x00000000ffe00000)
 * 		from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 * 		to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 * 	ParOldGen       total 10240K, used 8192K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
 * 		object space 10240K, 80% used [0x00000000fec00000,0x00000000ff400020,0x00000000ff600000)
 * 	Metaspace       used 2650K, capacity 4486K, committed 4864K, reserved 1056768K
 * 		class space    used 286K, capacity 386K, committed 512K, reserved 1048576K
 * 
 *测试结果可以认为在Parallel Scavenge收集器下,新生代容纳不下新对象时直接放入老年代,新生代中并不发生GC,移动存活的对象到survivor区
 */
public class TestDynamicTenuringThreshold {

	private static final int _1MB = 1024*1024;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[_1MB /4];
		allocation2 = new byte[_1MB /4];
		allocation3 = new byte[4 * _1MB];
		/*
		 * allocation1+allocation2大于survivor区的一半, 当新生代收集器为Serial收集器时,allocation1,allocation2会移动到老年代
		 * 并且当注释掉allocation1,allocation2其中一个,达不到相同年龄所有对象大小的总和大于survivor区的一半,则不会进入老年代
		 */
		allocation4 = new byte[4 * _1MB];
		allocation4 = null;
		allocation4 = new byte[4 * _1MB];
	}

}
