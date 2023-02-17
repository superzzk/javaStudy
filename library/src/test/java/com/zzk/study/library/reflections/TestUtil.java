package com.zzk.study.library.reflections;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2022/12/3 4:42 PM
 */
public class TestUtil {

    public static Matcher<Collection<Class<?>>> annotatedWith(final Class<? extends Annotation> annotation) {
        return new Match<Collection<Class<?>>>() {
            public boolean matches(Object o) {
                for (Class<?> c : (Iterable<Class<?>>) o) {
                    List<Class<? extends Annotation>> annotationTypes = Stream.of(c.getAnnotations()).map(Annotation::annotationType).collect(Collectors.toList());
                    if (!annotationTypes.contains(annotation)) return false;
                }
                return true;
            }
        };
    }

    public static <T> Matcher<Collection<? super T>> are(final T... ts) {
        final Collection<?> c1 = Arrays.asList(ts);
        return new Match<Collection<? super T>>() {
            public boolean matches(Object o) {
                Collection<?> c2 = (Collection<?>) o;
                return c1.containsAll(c2) && c2.containsAll(c1);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(Arrays.toString(ts));
            }
        };
    }

    public abstract static class Match<T> extends BaseMatcher<T> {
        public void describeTo(Description description) { }
    }
}
