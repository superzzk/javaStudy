package zzk.study.java.core.jar;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

@Slf4j
public class JarAnalyzerTest {

    @Test
    public void test() throws IOException {
        System.out.println("dddd");
        String jarPath = "/Users/kun/project/notification/notification-processor/notification-processor-service/target/notification-processor-service-0.0.1-SNAPSHOT.jar";
        JarAnalyzer jarAnalyzer = new JarAnalyzer(jarPath);
        log.info("jar包所有属性：");

        jarAnalyzer.getJarAttrs().forEach((key, value) -> {
            log.info("key={}，value={}", key, value);
        });

        log.info("MainClass -> {}", jarAnalyzer.getProgamClass());
        log.info("jar包含有的类：");

        jarAnalyzer.getClassNamesFromJarFile(new File(jarPath)).forEach(clazz -> {
            log.info("className ->{}", clazz);
        });
    }
}