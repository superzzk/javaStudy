package zzk.study.java.core.designpattern.dsl.library;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandChannel {
    public void send(String code) {
        log.info("command channel send:{}",code);
    }
}
