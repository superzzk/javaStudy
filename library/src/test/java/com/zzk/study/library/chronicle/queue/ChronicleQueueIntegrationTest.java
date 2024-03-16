package com.zzk.study.library.chronicle.queue;

import net.openhft.chronicle.Chronicle;
import net.openhft.chronicle.ChronicleQueueBuilder;
import net.openhft.chronicle.ExcerptAppender;
import net.openhft.chronicle.ExcerptTailer;
import net.openhft.chronicle.tools.ChronicleTools;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class ChronicleQueueIntegrationTest {

    @Test
    public void givenSetOfValues_whenWriteToQueue_thenWriteSuccesfully() throws IOException {
        // build queue
        File queueDir = Files.createTempDirectory("chronicle-queue").toFile();
        ChronicleTools.deleteOnExit(queueDir.getPath());

        Chronicle chronicle = ChronicleQueueBuilder.indexed(queueDir).build();
        String stringVal = "Hello World";
        int intVal = 101;
        long longVal = System.currentTimeMillis();
        double doubleVal = 90.00192091d;

        // appender
        ExcerptAppender appender = chronicle.createAppender();
        appender.startExcerpt();
        appender.writeUTF(stringVal);
        appender.writeInt(intVal);
        appender.writeLong(longVal);
        appender.writeDouble(doubleVal);
        appender.finish();
        appender.close();

        // tailer
        ExcerptTailer tailer = chronicle.createTailer();
        while (tailer.nextIndex()) {
            assertEquals(stringVal, tailer.readUTF());
            assertEquals(intVal, tailer.readInt());
            assertEquals(longVal, tailer.readLong());
            assertEquals((Double) doubleVal, (Double) tailer.readDouble());
        }
        tailer.finish();
        tailer.close();
        chronicle.close();
    }
}
