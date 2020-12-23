package zzk.study.java.core.string;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class StringDemo {

	@Test
	public void whenSplit_thenCorrect() {
		String s = "Welcome to Baeldung";
		String[] expected1 = new String[] { "Welcome", "to", "Baeldung" };
		String[] expected2 = new String[] { "Welcome", "to Baeldung" };

		assertArrayEquals(expected1, s.split(" "));
		assertArrayEquals(expected2, s.split(" ", 2));
	}

	@Test
	public void split_demo() {
		String temp = "boo:and:foo";
		Assert.assertEquals("[boo, and, foo]", Arrays.toString(temp.split(":")));
		Assert.assertEquals("[b, , :and:f]", Arrays.toString(temp.split("o")));

		Assert.assertEquals("[boo, and:foo]",Arrays.toString(temp.split(":",2)));
		Assert.assertEquals("[boo, and, foo]",Arrays.toString(temp.split(":",5)));
		Assert.assertEquals("[boo, and, foo]",Arrays.toString(temp.split(":",-2)));
		Assert.assertEquals("[b, , :and:f, , ]",Arrays.toString(temp.split("o",5)));
		Assert.assertEquals("[b, , :and:f, , ]",Arrays.toString(temp.split("o",-2)));
		//without trailing blank
		Assert.assertEquals("[b, , :and:f]",Arrays.toString(temp.split("o",0)));
		//leading blank
		Assert.assertEquals("[, oo:and:foo]",Arrays.toString(temp.split("b",0)));
	}

}
