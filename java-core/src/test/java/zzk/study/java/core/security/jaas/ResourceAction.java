package zzk.study.java.core.security.jaas;

import java.security.PrivilegedAction;

public class ResourceAction implements PrivilegedAction<Void> {
    @Override
    public Void run() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new ResourcePermission("test_resource"));
        }
        System.out.println("I have access to test_resource !");
        return null;
    }
}
