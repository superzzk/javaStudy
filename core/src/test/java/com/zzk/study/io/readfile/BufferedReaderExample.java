package com.zzk.study.io.readfile;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BufferedReaderExample {

	protected static ArrayList<String> generateArrayListFromFile(String filename) throws IOException {
		ArrayList<String> result = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			while (br.ready()) {
				result.add(br.readLine());
			}
			return result;
		}
	}

	protected static final String TEXT_FILENAME = "src/test/resources/io/readfile/sampleTextFile.txt";

	@Test
	public void whenParsingExistingTextFile_thenGetArrayList() throws IOException {
		List<String> lines = BufferedReaderExample.generateArrayListFromFile(TEXT_FILENAME);
        assertEquals("File does not has 2 lines", 2, lines.size());
	}
}
