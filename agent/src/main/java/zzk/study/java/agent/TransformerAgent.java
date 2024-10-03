package zzk.study.java.agent;

import java.lang.instrument.Instrumentation;

public class TransformerAgent {
	public static void premain(String agentArgs, Instrumentation instrumentation)  {

		instrumentation.addTransformer(new ClassFileTransformerDemo());
		System.out.println("我是两个参数的 Java Agent premain");
	}

	//有存在两个参数的premain时，此方法被忽略
	public static void premain(String agentArgs){
		System.out.println("我是一个参数的 Java Agent premain");
	}
}
