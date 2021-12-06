package zzk.study.java.core.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static org.junit.Assert.*;

public class SymLinkExampleManualTest {

    @Test
    public void whenUsingFiles_thenCreateSymbolicLink() throws IOException {
        SymLinkExample example = new SymLinkExample();
        Path filePath = example.createTextFile();
        Path linkPath = Paths.get(".", "symbolic_link.txt");
        example.createSymbolicLink(linkPath, filePath);
        assertTrue(Files.isSymbolicLink(linkPath));
    }

    @Test
    public void whenUsingFiles_thenCreateHardLink() throws IOException {
        SymLinkExample example = new SymLinkExample();
        Path filePath = example.createTextFile();
        Path linkPath = Paths.get(".", "hard_link.txt");
        example.createHardLink(linkPath, filePath);
        assertFalse(Files.isSymbolicLink(linkPath));
        assertEquals(filePath.toFile()
            .length(),
            linkPath.toFile()
                .length());
    }

    public static class SymLinkExample {

        public void createSymbolicLink(Path link, Path target) throws IOException {
            if (Files.exists(link)) {
                Files.delete(link);
            }
            Files.createSymbolicLink(link, target);
        }

        public void createHardLink(Path link, Path target) throws IOException {
            if (Files.exists(link)) {
                Files.delete(link);
            }
            Files.createLink(link, target);
        }

        public Path createTextFile() throws IOException {
            byte[] content = IntStream.range(0, 10000)
                    .mapToObj(i -> i + System.lineSeparator())
                    .reduce("", String::concat)
                    .getBytes(StandardCharsets.UTF_8);
            Path filePath = Paths.get(".", "target_link.txt");
            Files.write(filePath, content, CREATE, TRUNCATE_EXISTING);
            return filePath;
        }

        public void printLinkFiles(Path path) throws IOException {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path file : stream) {
                    if (Files.isDirectory(file)) {
                        printLinkFiles(file);
                    } else if (Files.isSymbolicLink(file)) {
                        System.out.format("File link '%s' with target '%s'%n", file, Files.readSymbolicLink(file));
                    }
                }
            }
        }

        public static void main(String[] args) throws IOException {
            new SymLinkExample().printLinkFiles(Paths.get("."));
        }

    }

}
