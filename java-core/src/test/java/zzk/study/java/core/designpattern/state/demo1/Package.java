package zzk.study.java.core.designpattern.state.demo1;

public class Package {

    private PackageState state = new OrderedState();

    public PackageState getState() {
        return state;
    }

    public void setState(PackageState state) {
        this.state = state;
    }

    public void previousState() {
        state.prev(this);
    }

    public void nextState() {
        state.next(this);
    }

    public void printStatus() {
        state.printStatus();
    }
}
