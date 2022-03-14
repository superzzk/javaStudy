package zzk.study.java.core.lang.java8.stream;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CollectDemo {
    @Test
    public void collect_test(){
        List<String> strList = Stream.builder()
                .add(3)
                .add("bbb")
                .add("ccc")
                .build().parallel()
                .collect(ArrayList::new,
                        (list, str) -> list.add(String.valueOf(str)),
                        ArrayList::addAll);
        Assertions.assertEquals(3, strList.size());
    }
}
