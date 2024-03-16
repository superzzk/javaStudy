package zzk.study.java.core.java8.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/6/5 10:41 PM
 */
public class CollectorsDemo {

    @Test
    public void collect_to_list() {
        Iterable<String> iterable = Arrays.asList("Testing", "Iterable", "conversion", "to", "Stream");
        List<String> result = StreamSupport.stream(iterable.spliterator(), false).map(String::toUpperCase).collect(Collectors.toList());

        assertThat(result, contains("TESTING", "ITERABLE", "CONVERSION", "TO", "STREAM"));
    }

    @Test
    public void collect_to_map(){
        final List<String> strings = Arrays.asList("a", "b", "c", "d");
        final Map<String, Integer> collect = strings.stream().collect(Collectors.toMap(Function.identity(), v -> v.length()));
        System.out.println(collect); // {a=1, b=1, c=1, d=1}

        final Map<String, Integer> collect1 = collect.entrySet().stream()
                .collect(Collectors.toMap(entry->{return entry.getKey().toUpperCase();}, Map.Entry::getValue));
        System.out.println(collect1); // {A=1, B=1, C=1, D=1}
    }
}
