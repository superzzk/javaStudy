package zzk.study.java.core.designpattern.behavioral.chain_of_responsibility.interceptor;

public class GetStrImpl implements IGetStr {

    @Override
    public String getStrZero() {
        return "0";
    }

    @Override
    public String getStrOne() {
        return "1";
    }
}