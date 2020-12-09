package zzk.study.java.core.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.security.AccessControlException;
import java.security.BasicPermission;

public class SecurityManagerUnitTest {

    private static final String TESTING_SECURITY_POLICY = "file:src/test/resources/security/testing.policy";

    @Before
    public void setUp() {
        System.setProperty("java.security.policy", TESTING_SECURITY_POLICY);
        System.setSecurityManager(new SecurityManager());
    }

    @After
    public void tearDown() {
        System.setSecurityManager(null);
    }

    @Test(expected = AccessControlException.class)
    public void whenSecurityManagerIsActive_thenNetworkIsNotAccessibleByDefault() throws IOException {
        new URL("http://www.google.com").openConnection().connect();
    }

    @Test(expected = AccessControlException.class)
    public void whenUnauthorizedClassTriesToAccessProtectedOperation_thenAnExceptionIsThrown() {
        new Service().operation();
    }

    public static class CustomPermission extends BasicPermission {
        public CustomPermission(String name) {
            super(name);
        }

        public CustomPermission(String name, String actions) {
            super(name, actions);
        }
    }

    public static class Service {

        public static final String OPERATION = "my-operation";

        public void operation() {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(new CustomPermission(OPERATION));
            }
            System.out.println("Operation is executed");
        }
    }

}
