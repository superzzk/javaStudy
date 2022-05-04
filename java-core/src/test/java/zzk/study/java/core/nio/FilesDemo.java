package zzk.study.java.core.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.UUID;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class FilesDemo {
    private final String HOME = System.getProperty("user.home");

    @Test
    public void fileExist_regularFile() {
        System.out.println(HOME);
        final Path p = Paths.get(HOME);
        assertTrue(Files.exists(p));
        assertTrue(Files.notExists(Paths.get(HOME + "some_not_exist_file")));

        assertFalse(Files.isRegularFile(p));
    }

    @Test
    public void readable_writable_executable() {
        Path p = Paths.get(HOME);
        assertTrue(Files.isReadable(p));
        assertTrue(Files.isWritable(p));
        assertTrue(Files.isExecutable(p));
    }

    @Test
    public void sameFile() throws IOException {
        Path p1 = Paths.get(HOME);
        Path p2 = Paths.get(HOME);
        assertTrue(Files.isSameFile(p1, p2));
    }

    @Test
    public void createFile_delete() throws IOException {
        String fileName = "myfile_" + UUID.randomUUID().toString() + ".txt";
        Path p = Paths.get(HOME + "/" + fileName);
        assertFalse(Files.exists(p));

        Files.createFile(p);
        assertTrue(Files.exists(p));

        assertTrue(Files.deleteIfExists(p));
    }

    @Test
    public void createDirectory() throws IOException {
        String dirName = "myDir_" + UUID.randomUUID().toString();
        Path p = Paths.get(HOME + "/" + dirName);
        assertFalse(Files.exists(p));

        Files.createDirectory(p);

        assertTrue(Files.exists(p));
        assertFalse(Files.isRegularFile(p));
        assertTrue(Files.isDirectory(p));

        assertTrue(Files.deleteIfExists(p));
    }

    @Test(expected = NoSuchFileException.class)
    public void givenDirPath_whenFailsToCreateRecursively_thenCorrect() throws IOException {
        String dirName = "myDir_" + UUID.randomUUID().toString() + "/subdir";
        Path p = Paths.get(HOME + "/" + dirName);
        assertFalse(Files.exists(p));

        Files.createDirectory(p);
    }

    @Test
    public void createDirectories() throws IOException {
        Path dir = Paths.get(
                HOME + "/myDir_" + UUID.randomUUID().toString());
        Path subdir = dir.resolve("subdir");
        assertFalse(Files.exists(dir));
        assertFalse(Files.exists(subdir));

        Files.createDirectories(subdir);

        assertTrue(Files.exists(dir));
        assertTrue(Files.exists(subdir));
    }

    @Test
    public void createTempFile() throws IOException {
        String prefix = "log_";
        String suffix = ".txt";
        Path p = Paths.get(HOME + "/");

        //create file like log_8821081429012075286.txt
        Files.createTempFile(p, prefix, suffix);
        assertTrue(Files.exists(p));

        //crate file like 8600179353689423985.tmp
        Files.createTempFile(Paths.get(HOME + "/"), null, null);

        //create file in system default temp folder
        Path tempFile = Files.createTempFile(null, null);
        System.out.println(tempFile);
    }

    @Test
    public void copy() throws IOException {
        Path dir1 = Paths.get(HOME + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(HOME + "/otherdir_" + UUID.randomUUID().toString());

        Files.createDirectory(dir1);
        Files.createDirectory(dir2);

        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");

        Files.createFile(file1);

        assertTrue(Files.exists(file1));
        assertFalse(Files.exists(file2));

        Files.copy(file1, file2);

        assertTrue(Files.exists(file2));
    }

    @Test
    public void move() throws IOException {
        Path dir1 = Paths.get(HOME + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(HOME + "/otherdir_" + UUID.randomUUID().toString());

        Files.createDirectory(dir1);
        Files.createDirectory(dir2);

        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");
        Files.createFile(file1);

        assertTrue(Files.exists(file1));
        assertFalse(Files.exists(file2));

        Files.move(file1, file2);

        assertTrue(Files.exists(file2));
        assertFalse(Files.exists(file1));
    }

    @Test
    public void newDirectorStream() throws IOException {
        final DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get(HOME));
        paths.forEach(path->{
            System.out.println(path);
        });
    }

}
