package io.readfile;

import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class ScannerExample {

	protected static ArrayList<Integer> generateIntArrayListFromFile(String filename) throws IOException {
		ArrayList<Integer> result = new ArrayList<>();
		try (Scanner s = new Scanner(new FileReader(filename))) {
			while (s.hasNext()) {
				result.add(s.nextInt());
			}
			return result;
		}
	}

	protected static ArrayList<String> generateStringArrayListFromFile(String filename) throws IOException {
		ArrayList<String> result = new ArrayList<>();
		try (Scanner s = new Scanner(new FileReader(filename))) {
			while (s.hasNext()) {
				result.add(s.nextLine());
			}
			return result;
		}
	}

	@Test
	public void whenParsingExistingTextFile_thenGetIntArrayList() throws IOException {
		List<Integer> numbers = generateIntArrayListFromFile("src/test/resources/sampleNumberFile.txt");
		assertTrue("File does not has 2 lines", numbers.size() == 2);
	}

	@Test
	public void whenParsingExistingTextFile_thenGetArrayList() throws IOException {
		List<String> lines = generateStringArrayListFromFile("src/test/resources/sampleTextFile.txt");
		assertTrue("File does not has 2 lines", lines.size() == 2);
	}
}
