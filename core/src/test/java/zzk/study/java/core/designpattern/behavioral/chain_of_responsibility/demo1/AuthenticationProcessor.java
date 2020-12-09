package zzk.study.java.core.designpattern.behavioral.chain_of_responsibility.demo1;

public abstract class AuthenticationProcessor {

    // next element in chain or responsibility
    public AuthenticationProcessor nextProcessor;
    
    public AuthenticationProcessor(AuthenticationProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public abstract boolean isAuthorized(AuthenticationProvider authProvider);
}
