package com.zzk.study.library.guava.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/6/19 1:23 PM
 */
public class ThreadFactoryBuilderDemo {

    @Test
    public void build(){
        final ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("Orders-%d")
                .setDaemon(true)
                .build();
        final ExecutorService executorService = Executors.newFixedThreadPool(10, threadFactory);
    }
}
