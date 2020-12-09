package zzk.study.java.core.io.readfile;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FilesReadLinesExample {

    protected static ArrayList<String> generateArrayListFromFile(String filename) throws IOException {
        
        List<String> result = Files.readAllLines(Paths.get(filename));
        
        return (ArrayList<String>) result;
    }

    protected static final String TEXT_FILENAME = "src/test/resources/sampleTextFile.txt";

    @Test
    public void whenParsingExistingTextFile_thenGetArrayList() throws IOException {
        List<String> lines = FilesReadLinesExample.generateArrayListFromFile(TEXT_FILENAME);
        assertTrue("File does not has 2 lines", lines.size() == 2);
    }

}
