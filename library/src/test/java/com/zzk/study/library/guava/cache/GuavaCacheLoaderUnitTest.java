package com.zzk.study.library.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

public class GuavaCacheLoaderUnitTest {
    int callCount = 0;

    @Test
    public void givenCacheLoader_whenGettingItemTwice_shouldOnlyCallOnce() throws ExecutionException {

        final LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(final String s) throws Exception {
                        return slowMethod(s);
                    }
                });

        String value = loadingCache.get("key");
        value = loadingCache.get("key");

        assertThat(callCount).isEqualTo(1);
        assertThat(value).isEqualTo("key");
    }

    @Test
    public void givenCacheLoader_whenRefreshingItem_shouldCallAgain() throws ExecutionException {

        final LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(final String s) throws Exception {
                        return slowMethod(s);
                    }
                });

        String value = loadingCache.get("key");
        loadingCache.refresh("key");

        assertThat(callCount).isEqualTo(2);
        assertThat(value).isEqualTo("key");
    }

    private String slowMethod(final String s) {
        callCount++;
        return s;
    }
}