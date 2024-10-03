package com.zzk.study.agent.demo03;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class DynamicAgent {
    public static void agentmain(String arg, Instrumentation inst) {
        Class[] classes = inst.getAllLoadedClasses();
        for(Class cls :classes){
            System.out.println(cls.getName());
        }
    }

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine vm = VirtualMachine.attach("30572");
        try{
            vm.loadAgent("D:\\Documents\\project\\javaStudy\\agent\\demo03\\target\\agent-demo03-1.0-SNAPSHOT.jar");
        }finally {
            vm.detach();
        }
    }
}
