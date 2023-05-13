package com.zzk.study.library.guava.collections.list;

import com.google.common.base.Predicates;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.contains;

public class GuavaListsUnitTest {
    @Test
    public void creat() {
        final List<String> names = Lists.newArrayList("John", "Adam", "Jane");

        names.add("Tom");
        assertEquals(4, names.size());

        names.remove("Adam");
        assertThat(names, contains("John", "Jane", "Tom"));
    }

    @Test
    public void reverse() {
        final List<String> names = Lists.newArrayList("John", "Adam", "Jane");

        final List<String> reversed = Lists.reverse(names);
        assertThat(reversed, contains("Jane", "Adam", "John"));
    }

    @Test
    public void characters_of() {
        final List<Character> chars = Lists.charactersOf("John");

        assertEquals(4, chars.size());
        assertThat(chars, contains('J', 'o', 'h', 'n'));
    }

    @Test
    public void copy_to_set() {
        final List<Character> chars = Lists.newArrayList('h', 'e', 'l', 'l', 'o');
        assertEquals(5, chars.size());

        final List<Character> result = ImmutableSet.copyOf(chars).asList();
        assertThat(result, contains('h', 'e', 'l', 'o'));
    }

    @Test
    public void whenRemoveNullFromList_thenRemoved() {
        final List<String> names = Lists.newArrayList("John", null, "Adam", null, "Jane");
        Iterables.removeIf(names, Predicates.isNull());

        assertEquals(3, names.size());
        assertThat(names, contains("John", "Adam", "Jane"));
    }

    @Test
    public void copyOf() {
        final List<String> names = Lists.newArrayList("John", "Adam", "Jane");

        names.add("Tom");
        assertEquals(4, names.size());

        final ImmutableList<String> immutable = ImmutableList.copyOf(names);
        assertThat(immutable, contains("John", "Adam", "Jane", "Tom"));
    }
}