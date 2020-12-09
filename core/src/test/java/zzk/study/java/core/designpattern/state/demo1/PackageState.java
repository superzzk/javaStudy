package zzk.study.java.core.designpattern.state.demo1;

public interface PackageState {

    void next(Package pkg);

    void prev(Package pkg);

    void printStatus();
}