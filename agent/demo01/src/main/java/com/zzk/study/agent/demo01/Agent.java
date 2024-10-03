package com.zzk.study.agent;
/**
 * java -cp:. -javaagent:/pathToJar=MyAgent zzk.study.java.agent.demo01.Agent
 */
public class Agent {
    public static void premain(String args) {
        System.out.println("hello from: " +args);
    }
}
