package com.zzk.study;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.utility.JavaModule;

import java.io.PrintStream;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Agent {
    public static void premain(String args, Instrumentation inst) {
        System.out.println("from agent");
        try {
            new AgentBuilder.Default()
                    .type(hasSuperType(named("com.zzk.study.App")))
                    .transform(new AgentBuilder.Transformer() {
                        @Override
                        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, ProtectionDomain protectionDomain) {
                            try {
                                System.out.println("Class was loaded: " + typeDescription.getCanonicalName());
                                return builder
                                        .method(any())
                                        .intercept(MethodCall.invoke(
                                                        PrintStream.class.getMethod("println", String.class))
                                                .onField(System.class.getField("out"))
                                                .with("hello word from agent")
                                                .andThen(SuperMethodCall.INSTANCE));
                            } catch (NoSuchMethodException | NoSuchFieldException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    })
                    .installOn(inst);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
