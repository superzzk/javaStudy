package zzk.study.java.core;

/**
 * -javaagent:D:/study/javaStudy/agent/target/agent-1.0-SNAPSHOT.jar
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("one");
		System.out.println("----" + new Dog().say());
	}
}
