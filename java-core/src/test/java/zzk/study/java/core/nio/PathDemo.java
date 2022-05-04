package zzk.study.java.core.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;

public class PathDemo {
    private static String HOME = System.getProperty("user.home");

    @Test
    public void get() {
        assertEquals("/articles/baeldung", Paths.get("/articles/baeldung").toString());
        assertEquals("/articles/baeldung", Paths.get("/articles","baeldung").toString());
    }

    @Test
    public void getFileName() {
        Path p = Paths.get("/articles/baeldung/logs");
        Path fileName = p.getFileName();
        assertEquals("logs", fileName.toString());

        Path p2 = Paths.get("/articles/baeldung/");
        assertEquals("baeldung", p2.getFileName().toString());
    }

    @Test
    public void subpath() {
        Path p = Paths.get("/articles/baeldung/logs");

        Path subPath1 = p.subpath(0,1);
        Path subPath2 = p.subpath(0,2);

        assertEquals("articles", subPath1.toString());
        assertEquals("articles/baeldung", subPath2.toString());
        assertEquals("articles/baeldung/logs", p.subpath(0, 3).toString());
        assertEquals("baeldung", p.subpath(1, 2).toString());
        assertEquals("baeldung/logs", p.subpath(1, 3).toString());
        assertEquals("logs", p.subpath(2, 3).toString());
    }

    @Test
    public void getParent() {
        Path p1 = Paths.get("/articles/baeldung/logs");
        Path p2 = Paths.get("/articles/baeldung");
        Path p3 = Paths.get("/articles");
        Path p4 = Paths.get("/");

        assertEquals("/articles/baeldung", p1.getParent().toString());
        assertEquals("/articles", p2.getParent().toString());
        assertEquals("/", p3.getParent().toString());
        assertEquals(null, p4.getParent());
    }

    @Test
    public void getRoot() {
        Path p1 = Paths.get("/articles/baeldung/logs");
        assertEquals("/", p1.getRoot().toString());
    }

    @Test
    public void normalize() {
        Path p1 = Paths.get("/home/./baeldung/articles");
        assertEquals("/home/baeldung/articles", p1.normalize().toString());

        Path p2 = Paths.get("/home/baeldung/../articles");
        assertEquals("/home/articles", p2.normalize().toString());
    }

    @Test
    public void givenPath_whenConvertsToBrowseablePath_thenCorrect() {
        Path p = Paths.get("/home/baeldung/articles.html");
        assertEquals("file:///home/baeldung/articles.html",
                p.toUri().toString());
    }

    @Test
    public void toAbsolutePath() {
        Path p = Paths.get("/home/baeldung/articles.html");
        assertEquals("/home/baeldung/articles.html",
                p.toAbsolutePath().toString());
    }

    @Test
    public void toRealPath_normalize() throws IOException {
        Path p = Paths.get(HOME);
        Path p2 = p.resolve("..").resolve("kun");
        System.out.println(p2);
        System.out.println(p2.toRealPath());
        System.out.println(p2.normalize());
        assertEquals(HOME, p.toRealPath().toString());
    }

    @Test(expected = NoSuchFileException.class)
    public void toRealPathWhenNotExist() throws IOException {
        Path p = Paths.get("/home/baeldung/articles.html");
        p.toRealPath();
    }

    @Test
    public void resolve() {
        Path p = Paths.get("/baeldung/articles");
        Path p2 = p.resolve("java");
        assertEquals("/baeldung/articles/java", p2.toString());
    }

    @Test
    public void givenPathWithRoot_whenResolutionRetainsIt_thenCorrect2() {
        Path p = Paths.get("/baeldung/articles");
        Path p2 = p.resolve("/java");
        assertEquals("/java", p2.toString());
    }

    @Test
    public void relative_sibling() {
        Path p1 = Paths.get("articles");
        Path p2 = Paths.get("authors");

        Path p1_rel_p2 = p1.relativize(p2);
        Path p2_rel_p1 = p2.relativize(p1);

        assertEquals("../authors", p1_rel_p2.toString());
        assertEquals("../articles", p2_rel_p1.toString());
    }

    @Test
    public void relative_subpath() {
        Path p1 = Paths.get("/baeldung");
        Path p2 = Paths.get("/baeldung/authors/articles");

        assertEquals("authors/articles", p1.relativize(p2).toString());
        assertEquals("../..", p2.relativize(p1).toString());
    }

    @Test
    public void equals() {
        Path p1 = Paths.get("/baeldung/articles");
        Path p2 = Paths.get("/baeldung/articles");
        Path p3 = Paths.get("/baeldung/authors");

        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(p3));
    }

    @Test
    public void startsWith() {
        Path p1 = Paths.get("/baeldung/articles");
        assertTrue(p1.startsWith("/baeldung"));
    }

    @Test
    public void getRootDirectories(){
        FileSystems.getDefault().getRootDirectories().forEach(path -> {
            try {
                Files.list(path).forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void forEach(){
        final Path path = FileSystems.getDefault().getPath("/Users/Kun/Document");
        List<String> expect = Arrays.asList("Users", "Kun", "Document");
        List<String> result = new ArrayList<>();
        path.forEach(p->{result.add(p.toString());});
        assertEquals(expect, result);
    }
}
