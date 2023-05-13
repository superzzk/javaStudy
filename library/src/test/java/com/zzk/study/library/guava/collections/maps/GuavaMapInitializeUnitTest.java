package com.zzk.study.library.guava.collections.maps;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class GuavaMapInitializeUnitTest {

    @Test
    public void immutable() {
        Map<String, String> articles = ImmutableMap.of("Title", "My New Article", "Title2", "Second Article");
        
        assertThat(articles.get("Title"), equalTo("My New Article"));
        assertThat(articles.get("Title2"), equalTo("Second Article"));
    }

    @Test
    public void mutable() {
        Map<String, String> articles = Maps.newHashMap(ImmutableMap.of("Title", "My New Article", "Title2", "Second Article"));

        assertThat(articles.get("Title"), equalTo("My New Article"));
        assertThat(articles.get("Title2"), equalTo("Second Article"));
    }   
}
