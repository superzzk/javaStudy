package zzk.study.java.core.security.jaas;

import java.security.BasicPermission;

public class ResourcePermission extends BasicPermission {
    public ResourcePermission(String name) {
        super(name);
    }
}