package zzk.study.java.core.nio.filecopy;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class FileCopy {

    @Test
    public void demo() throws IOException {
        FileCopy.copy("/Users/kun/test/from/hhh","/Users/kun/test/to");
    }

    public static void copy(final String src, final String target) throws IOException {
        if (StringUtils.isEmpty(src) || StringUtils.isEmpty(target)) {
            throw new IllegalArgumentException("src and target required");
        }

        final String fileName = getFileName(src);

        try (FileChannel from = (FileChannel.open(Paths.get(src), StandardOpenOption.READ));
             FileChannel to = (FileChannel.open(Paths.get(target + "/" + fileName), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE))) {
            transfer(from, to, 0l, from.size());
        }
    }

    private static String getFileName(final String src) {
        assert StringUtils.isNotEmpty(src);

        final File file = new File(src);
        if (file.isFile()) {
            return file.getName();
        } else {
            throw new RuntimeException("src is not a valid file");
        }
    }

    private static void transfer(final FileChannel from, final FileChannel to, long position, long size) throws IOException {
        assert !Objects.isNull(from) && !Objects.isNull(to);

        while (position < size) {
            position += from.transferTo(position, Integer.MAX_VALUE, to);
        }
    }
}