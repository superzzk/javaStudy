package zzk.study.java.core.basic.string;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class StringDemo {

	@Test
	public void replace() {
		String str ="www.google.com";

		assertEquals("www.baidu.com", str.replace("google", "baidu"));
		assertEquals("www.google.com", str.replaceAll("(.*)taobao(.*)","baidu"));
		assertEquals("baidu", str.replaceAll("(.*)google(.*)", "baidu"));

		String s2 = "a/b/c";
		assertEquals("abc", s2.replace("/", ""));
	}

	@Test
	public void replaceAll() {
		String s1="javatpoint is a very good website";
		assertEquals("jevetpoint is e very good website", s1.replaceAll("a", "e"));

		s1="My name is Khan. My name is Bob. My name is Sonoo.";
		assertEquals("My name was Khan. My name was Bob. My name was Sonoo.", s1.replaceAll("is", "was"));
		assertEquals("MynameisKhan.MynameisBob.MynameisSonoo.", s1.replaceAll("\\s", ""));

		String str = "JavaTpoint";
		assertEquals(" J a v a T p o i n t ", str.replaceAll("", " "));

		String s = "my url with spaces";
		assertEquals("my-url-with-spaces", s.replaceAll("\\s+", "-"));
	}

	@Test
	public void whenSplit_thenCorrect() {
		String s = "Welcome to Baeldung";
		String[] expected1 = new String[] { "Welcome", "to", "Baeldung" };
		String[] expected2 = new String[] { "Welcome", "to Baeldung" };

		assertArrayEquals(expected1, s.split(" "));
		assertArrayEquals(expected2, s.split(" ", 2));

		s = "";
		final String[] res = s.split(",");
		System.out.println(Arrays.toString(res));
		assertEquals(1, res.length);

		s = "abc";
		final String[] res2 = s.split(",");
		System.out.println(Arrays.toString(res2));
		assertEquals(1, res2.length);
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

	/**
	 * shuffle
	 */
	@Test
	public void whenShufflingList_thenListIsShuffled() {
		String temp = "abcd1；abcd2";
		String[] result = StringUtils.split(temp, ";；");
		Assert.assertEquals(result[0],"abcd1");
		Assert.assertEquals(result[1],"abcd2");
	}

	@Test
	public void whenShufflingMapEntries_thenValuesAreShuffled() {
		Map<Integer, String> studentsById = new HashMap<>();
		studentsById.put(1, "Foo");
		studentsById.put(2, "Bar");
		studentsById.put(3, "Baz");
		studentsById.put(4, "Qux");

		System.out.println("Students before shuffling:");
		System.out.println(studentsById.values());

		List<Map.Entry<Integer, String>> shuffledStudentEntries = new ArrayList<>(studentsById.entrySet());
		Collections.shuffle(shuffledStudentEntries);

		List<String> shuffledStudents = shuffledStudentEntries.stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());

		System.out.println("Students after shuffling");
		System.out.println(shuffledStudents);
	}

	@Test
	public void whenShufflingSet_thenElementsAreShuffled() {
		Set<String> students = new HashSet<>(Arrays.asList("Foo", "Bar", "Baz", "Qux"));

		System.out.println("Set before shuffling:");
		System.out.println(students);

		List<String> studentList = new ArrayList<>(students);

		Collections.shuffle(studentList);
		System.out.println("Shuffled set elements:");
		System.out.println(studentList);
	}

	@Test
	public void whenShufflingWithSameRandomness_thenElementsAreShuffledDeterministically() {
		List<String> students_1 = Arrays.asList("Foo", "Bar", "Baz", "Qux");
		List<String> students_2 = Arrays.asList("Foo", "Bar", "Baz", "Qux");

		Collections.shuffle(students_1, new Random(5));
		Collections.shuffle(students_2, new Random(5));

		Assert.assertEquals(students_1, students_2);
	}

	@Test
	public void toCharArrayDemo(){
		String s="Shuffled set elements:";
		final char[] chars = s.toCharArray();
	}

	@Test
	public void indexOfDemo(){
		String s="Shuffled set elements:";
		final int locate = s.indexOf('s');
		Assert.assertEquals(9, locate);
	}
}
