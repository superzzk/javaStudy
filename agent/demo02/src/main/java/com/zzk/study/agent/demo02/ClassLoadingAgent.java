package com.zzk.study.agent.demo02;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class ClassLoadingAgent {
    public static void premain(String argument,
                               Instrumentation instrumentation) {
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(Module module,
                                    ClassLoader loader,
                                    String name,
                                    Class<?> typeIfLoaded,
                                    ProtectionDomain domain,
                                    byte[] buffer) {
                System.out.println("Class was loaded: " + name);
                return null;
            }
        });
    }
}
