package com.ltchen.java.jvm.three;

/**
 * 
 * @file : TestHandlePromotion.java
 * @date : 2017年4月22日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 测试空间分配担保
 *  * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:[-|+]HandlePromotionFailure
 * -verbose:gc 表示输出GC的详细信息
 * -Xms20M 设置JVM堆内存初始化大小为20M
 * -Xmx20M 设置JVM堆内存最大大小为20M
 * -Xmn10M 设置JVM堆内存最小大小为10M
 * -XX:+PrintGCDetails 打印GC回收的详细信息
 * -XX:SurvivorRatio=8 Edon和Survivor的比例是8：1：1.Survivor是有两个的
 * -XX:[-|+]HandlePromotionFailure 设置空间分配担保是否允许冒险
 * 
 * 本次测试环境的JVM为1.8,新生代采用了Parallel Scavenge收集器,测试结果如下：Unrecognized VM option 'HandlePromotionFailure'
 * HandlePromotionFailure参数在JDK6Update24后不再使用
 * JDK6Update24以后版本的的空间分配担保冒险默认为：只要老年代的连续空间大于新生代对象总大小或者历次晋升的平均大小就会进行MinorGC,否则进行Full GC
 */
public class TestHandlePromotion {

	private static final int _1MB = 1024*1024;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
		allocation1 = new byte[1 * _1MB];
		allocation2 = new byte[3 * _1MB];
		allocation3 = new byte[3 * _1MB];
		allocation1 = null;
		allocation4 = new byte[6 * _1MB];
		allocation5 = new byte[2 * _1MB];
		allocation6 = new byte[2 * _1MB];
//		allocation4 = null;
//		allocation5 = null;
//		allocation6 = null;
		allocation7 = new byte[2 * _1MB];
	}

}
