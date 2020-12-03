package com.zzk.study.lang.java8.optional;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalDemo {

    @Test
    public void test_of_ofNullable_orElse_get(){
        Integer value1 = null;
        Integer value2 = 10;

        //Optional.ofNullable - allows passed parameter to be null.
        Optional<Integer> a = Optional.ofNullable(value1);

        //Optional.of - throws NullPointerException if passed parameter is null
        Optional<Integer> b = Optional.of(value2);
        System.out.println(sum(a,b));
    }

    private Integer sum(Optional<Integer> a, Optional<Integer> b) {
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

    @Test
    public void test_orElseThrow(){
        Integer value1 = 10;
        Integer value2 = null;

        try {
            System.out.println(toS(value1));
            System.out.println(toS(value2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toS(Integer i) throws Exception {
        return Optional.ofNullable(i)
                .map(String::valueOf)
                .orElseThrow(()->new Exception("i is null"));
    }

    @Test
    public void test_orElse_orElseGet(){
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

	@Test
	public void test_map_flatMap() {
		Assert.assertEquals(Optional.of(Optional.of("STRING")),
				Optional.of("string").map(s -> Optional.of("STRING"))
		);

		Assert.assertEquals(Optional.of("STRING"),
				Optional.of("string").flatMap(s -> Optional.of("STRING"))
		);
	}
}
