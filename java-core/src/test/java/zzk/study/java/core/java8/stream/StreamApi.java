package zzk.study.java.core.java8.stream;

import java.util.List;
import java.util.stream.Stream;

public class StreamApi {

    public static String getLastElementUsingSkip(List<String> valueList) {
        long count = (long) valueList.size();
        Stream<String> stream = valueList.stream();
        return stream.skip(count - 1).findFirst().orElse(null);
    }

}
