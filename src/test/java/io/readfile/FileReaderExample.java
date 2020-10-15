package io.readfile;

import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FileReaderExample {

	protected static ArrayList<String> generateArrayListFromFile(String filename) throws IOException {
		ArrayList<String> result = new ArrayList<>();
		try (FileReader f = new FileReader(filename)) {
			StringBuffer sb = new StringBuffer();
			while (f.ready()) {
				char c = (char) f.read();
				if (c == '\n') {
					result.add(sb.toString());
					sb = new StringBuffer();
				} else {
					sb.append(c);
				}
			}
			if (sb.length() > 0) {
				result.add(sb.toString());
			}
		}
		return result;
	}

	protected static final String TEXT_FILENAME = "src/test/resources/sampleTextFile.txt";

	@Test
	public void whenParsingExistingTextFile_thenGetArrayList() throws IOException {
		List<String> lines = FileReaderExample.generateArrayListFromFile(TEXT_FILENAME);
		assertTrue("File does not has 2 lines", lines.size() == 2);
	}
}
