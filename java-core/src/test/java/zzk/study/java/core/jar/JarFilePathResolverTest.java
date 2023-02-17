package zzk.study.java.core.jar;

import lombok.Lombok;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/12/5 9:48 AM
 */
class JarFilePathResolverTest {

    @Test
    void byGetProtectionDomain() throws URISyntaxException {
        final String lombokJarPath = JarFilePathResolver.byGetProtectionDomain(Lombok.class);
        System.out.println(lombokJarPath);
        Assertions.assertTrue(lombokJarPath.endsWith(".jar"));
        Assertions.assertTrue(Files.exists(Paths.get(lombokJarPath)));
    }

    @Test
    void byGetResource() {
        final String lombokJarPath = JarFilePathResolver.byGetResource(Lombok.class);
        System.out.println(lombokJarPath);
        Assertions.assertTrue(lombokJarPath.endsWith(".jar"));
        Assertions.assertTrue(Files.exists(Paths.get(lombokJarPath)));
    }
}