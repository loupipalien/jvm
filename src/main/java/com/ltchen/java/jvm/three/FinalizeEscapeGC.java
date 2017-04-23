package com.ltchen.java.jvm.three;

/**
 * 
 * @file : FinalizeEscapeGC.java
 * @date : 2017年4月22日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 测试对象可在finalize方法中可进行一次自救的过程
 * JVM垃圾回收处理采用可达性算法判定对象的死活,基本思想：通过一系列的成为"GC ROOTs"作为对象的起点,从这些节点开始向下搜索,搜索所走过的
 * 路径称为引用链(Reference Chain),当对象到GC ROOTs没有任何引用链相连时,则证明此对象不可达
 * GC ROOTs对象包括：虚拟机栈(栈帧中的本地变量表)中引用的对象
 *                  方法区中类静态属性引用的对象
 *                  方法区中常量引用的对象
 *                  本地方法栈中JNI(即一般说的Native方法)引用的对象
 * 
 * 测试输出：finalize method executed!
 *          yes, i am still  alive :)
 *          no, i am dead :(
 */
public class FinalizeEscapeGC {

	public static FinalizeEscapeGC SAVA_HOOK = null;
	
	public void isAlive(){
		System.out.println("yes, i am still  alive :)");
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize method executed!");
		FinalizeEscapeGC.SAVA_HOOK = this;
	}

	public static void main(String[] args) throws InterruptedException {
		SAVA_HOOK = new FinalizeEscapeGC();
		
		//对象第一次成功拯救自己
		SAVA_HOOK = null;
		System.gc();
		//因为finalize方法的优先级比较低,所以暂停0.5秒等待finalize方法执行
		Thread.sleep(500L);
		if(SAVA_HOOK != null){
			SAVA_HOOK.isAlive();
		}
		else{
			System.out.println("no, i am dead :(");
		}
		
		//下面这段代码与上一段相同,但是这一次自救失败了
		SAVA_HOOK = null;
		System.gc();
		Thread.sleep(500L);
		if(SAVA_HOOK != null){
			SAVA_HOOK.isAlive();
		}
		else{
			System.out.println("no, i am dead :(");
		}
		
	}

}
