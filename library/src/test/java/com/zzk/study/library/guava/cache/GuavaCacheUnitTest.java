package com.zzk.study.guava.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;

public class GuavaCacheUnitTest {

    @Test
    public void whenCacheMiss_thenAutoLoad() {
        final CacheLoader<String, String> loader = getUpperCaseStringCacheLoader();
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);
        assertEquals(0, cache.size());
        assertEquals("HELLO", cache.getUnchecked("hello"));
        assertEquals(1, cache.size());
    }

    //eviction
    @Test
    public void whenCacheReachMaxSize_thenEviction() {
        final CacheLoader<String, String> loader = getUpperCaseStringCacheLoader();
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(3).build(loader);
        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("forth");
        assertEquals(3, cache.size());
        assertNull(cache.getIfPresent("first"));
        assertEquals("FORTH", cache.getIfPresent("forth"));
    }

    //eviction by weight
    @Test
    public void whenCacheReachMaxWeight_thenEviction() {
        final CacheLoader<String, String> loader = getUpperCaseStringCacheLoader();
        final Weigher<String, String> weighByLength = new Weigher<String, String>() {
            @Override
            public int weigh(final String key, final String value) {
                return value.length();
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumWeight(16)
                .weigher(weighByLength)
                .build(loader);
        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("last");
        assertEquals(3, cache.size());
        assertNull(cache.getIfPresent("first"));
        assertEquals("LAST", cache.getIfPresent("last"));
    }

    //eviction by time
    @Test
    public void whenEntryIdle_thenEviction() throws InterruptedException {
        final CacheLoader<String, String> loader = getUpperCaseStringCacheLoader();
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.MILLISECONDS)
                .build(loader);
        cache.getUnchecked("hello");
        assertEquals(1, cache.size());
        cache.getUnchecked("hello");
        Thread.sleep(3);
        cache.getUnchecked("test");
        assertEquals(1, cache.size());
        assertNull(cache.getIfPresent("hello"));
    }


    //eviction by time
    @Test
    public void whenEntryLiveTimeExpire_thenEviction() throws InterruptedException {
        final CacheLoader<String, String> loader = getUpperCaseStringCacheLoader();
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MILLISECONDS)
                .build(loader);
        cache.getUnchecked("hello");
        assertEquals(1, cache.size());
        Thread.sleep(3);
        cache.getUnchecked("test");
        assertEquals(1, cache.size());
        assertNull(cache.getIfPresent("hello"));
    }

    //weak keys
    //By default, both cache keys and values have strong references but we can make our cache store the keys using weak references using weakKeys(),
    //allowing the garbage collector to collect cache keys that are not referenced elsewhere.
    @Test
    public void whenWeekKeyHasNoRef_thenRemoveFromCache() {
        final CacheLoader<String, String> loader = getUpperCaseStringCacheLoader();
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().weakKeys().build(loader);
    }

    //soft keys
    //allow the garbage collector to collect our cached values by using softValues()
    @Test
    public void whenSoftValue_thenRemoveFromCache() {
        final CacheLoader<String, String> loader = getUpperCaseStringCacheLoader();
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().softValues().build(loader);
    }

    //null values
    //By default, Guava Cache will throw exceptions if you try to load a null value – as it doesn't make any sense to cache a null.
    //But if null value means something in your code, then you can make good use of the Optional class as in the following example:
    @Test
    public void whenNullValue_thenOptional() {
        final CacheLoader<String, Optional<String>> loader = new CacheLoader<String, Optional<String>>() {
            @Override
            public final Optional<String> load(final String key) {
                return Optional.ofNullable(getSuffix(key));
            }
        };
        final LoadingCache<String, Optional<String>> cache = CacheBuilder.newBuilder().build(loader);
        assertEquals("txt", cache.getUnchecked("text.txt").get());
        assertFalse(cache.getUnchecked("hello").isPresent());
    }

    //auto refresh
    //The value will actually be refreshed only when a corresponding entry is queried by get(key).
    @Test
    public void whenLiveTimeEnd_thenRefresh() {
        final CacheLoader<String, String> loader = getUpperCaseStringCacheLoader();
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().refreshAfterWrite(1, TimeUnit.MINUTES).build(loader);
    }

    @Test
    public void whenPreloadCache_thenUsePutAll() {
        final CacheLoader<String, String> loader = getUpperCaseStringCacheLoader();
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);
        final Map<String, String> map = new HashMap<String, String>();
        map.put("first", "FIRST");
        map.put("second", "SECOND");
        cache.putAll(map);
        assertEquals(2, cache.size());
    }

    @Test
    public void whenEntryRemovedFromCache_thenNotify() {
        final CacheLoader<String, String> loader = getUpperCaseStringCacheLoader();
        final RemovalListener<String, String> listener = new RemovalListener<String, String>() {
            @Override
            public void onRemoval(final RemovalNotification<String, String> n) {
                if (n.wasEvicted()) {
                    final String cause = n.getCause().name();
                    assertEquals(RemovalCause.SIZE.toString(), cause);
                }
            }
        };
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(listener)
                .build(loader);
        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("last");
        assertEquals(3, cache.size());
    }

    // UTIL

    @NotNull
    private CacheLoader<String, String> getUpperCaseStringCacheLoader() {
        return new CacheLoader<String, String>() {
            @Override
            public final String load(final String key) {
                return key.toUpperCase();
            }
        };
    }

    private String getSuffix(final String str) {
        final int lastIndex = str.lastIndexOf('.');
        if (lastIndex == -1) {
            return null;
        }
        return str.substring(lastIndex + 1);
    }

}