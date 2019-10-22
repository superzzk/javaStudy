package java8.optional;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalDemo {
    public static void main(String args[]) {
        test3();
    }

    private static void test1(){
        Integer value1 = null;
        Integer value2 = 10;

        //Optional.ofNullable - allows passed parameter to be null.
        Optional<Integer> a = Optional.ofNullable(value1);

        //Optional.of - throws NullPointerException if passed parameter is null
        Optional<Integer> b = Optional.of(value2);
        System.out.println(sum(a,b));
    }

    private static Integer sum(Optional<Integer> a, Optional<Integer> b) {
        //Optional.isPresent - checks the value is present or not

        System.out.println("First parameter is present: " + a.isPresent());
        System.out.println("Second parameter is present: " + b.isPresent());

        //Optional.orElse - returns the value if present otherwise returns
        //the default value passed.
        Integer value1 = a.orElse(new Integer(0));

        //Optional.get - gets the value, value should be present
        Integer value2 = b.get();
        return value1 + value2;
    }

    private static void test2(){
        Integer value1 = 10;
        Integer value2 = null;

        try {
            System.out.println(toS(value1));
            System.out.println(toS(value2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * orElseThrow
     */
    private static String toS(Integer i) throws Exception {
        return Optional.ofNullable(i)
                .map(String::valueOf)
                .orElseThrow(()->new Exception("i is null"));
    }

    /**
     * orElse
     * orElseGet
     */
    private static void test3(){
        List<String> names = Arrays.asList("Mal", "Wash", "Kaylee", "Inara",
                "Zoë", "Jayne", "Simon", "River", "Shepherd Book");
        Optional<String> first = names.stream()
                .filter(name -> name.startsWith("C"))
                .findFirst();
        System.out.println(first);
        System.out.println(first.orElse("None"));
        System.out.println(first.orElse(String.format("No result found in %s",
                names.stream().collect(Collectors.joining(", ")))));
        System.out.println(first.orElseGet(() ->
                String.format("No result found in %s",
                        names.stream().collect(Collectors.joining(", ")))
                )
        );
    }
}
