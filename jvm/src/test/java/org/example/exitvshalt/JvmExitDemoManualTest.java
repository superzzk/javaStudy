package org.example.exitvshalt;

import org.junit.Test;

public class JvmExitDemoManualTest {

    JvmExitAndHaltDemo jvmExitAndHaltDemo = new JvmExitAndHaltDemo();

    @Test
    public void givenProcessComplete_whenExitCalled_thenTriggerShutdownHook() {
        jvmExitAndHaltDemo.processAndExit();
    }

    @Test
    public void givenProcessComplete_whenHaltCalled_thenDoNotTriggerShutdownHook() {
        jvmExitAndHaltDemo.processAndHalt();
    }

    public static class JvmExitAndHaltDemo {

        static {
            Runtime.getRuntime()
                    .addShutdownHook(new Thread(() -> {
                        System.out.println("Shutdown hook initiated.");
                    }));
        }

        public void processAndExit() {
            process();
            System.out.println("Calling System.exit().");
            System.exit(0);
        }

        public void processAndHalt() {
            process();
            System.out.println("Calling Runtime.getRuntime().halt().");
            Runtime.getRuntime()
                    .halt(0);
        }

        private void process() {
            System.out.println("Process started.");
        }

    }


}
