package com.ltchen.java.jvm;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * @file : MethodAreaOOM.java
 * @date : 2017年4月8日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : JVM方法区(非堆)内存溢出异常测试：java.lang.OutOfMemoryError
 * JDK6(永久代是HotSpot中方法区(非堆)的实现) VM args: -verbose:gc -XX:PermSize=10M -XX:MaxPermSize=10M
 * JDK8(永久代被移除,功能被元空间替代,放在本地堆内存中) VM args: -verbose:gc -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
 * -verbose:gc 表示输出GC的详细信息
 * -XX:PermSize=10M 设置永久代内存初始值大小
 * -XX:MaxPermSize=10M 设置永久代内存最大值大小
 * -XX:MetaspaceSize=10M 设置元空间内存初始值大小
 * -XX:MaxMetaspaceSize=10M 设置元空间内存最大值大小
 * 
 * JDK6:运行报错：java.lang.OutOfMemoryError: PermGen space
 * JDK8:运行报错：java.lang.OutOfMemoryError: Metaspace
 */
public class MethodAreaOOM {

	static class OOMObject{}
	
	public static void main(String[] args) {
		while(true){
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(OOMObject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {
				@Override
				public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
					return methodProxy.invokeSuper(obj, args);
				}
			});
			enhancer.create();
		}
	}

}
