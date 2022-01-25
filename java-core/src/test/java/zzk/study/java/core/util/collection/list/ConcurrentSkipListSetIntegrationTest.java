package zzk.study.java.core.util.collection.list;

import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConcurrentSkipListSetIntegrationTest {

    @Test
    public void givenThreadsProducingEvents_whenGetForEventsFromLastMinute_thenReturnThoseEventsInTheLockFreeWay() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        EventWindowSort eventWindowSort = new EventWindowSort();
        int numberOfThreads = 2;
        //when
        Runnable producer = () -> IntStream
          .rangeClosed(0, 100)
          .forEach(index -> eventWindowSort.acceptEvent(new Event(ZonedDateTime
            .now()
            .minusSeconds(index), UUID
            .randomUUID()
            .toString())));

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(producer);
        }

        Thread.sleep(500);

        ConcurrentNavigableMap<ZonedDateTime, String> eventsFromLastMinute = eventWindowSort.getEventsFromLastMinute();

        long eventsOlderThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isBefore(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();
        assertEquals(eventsOlderThanOneMinute, 0);

        long eventYoungerThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isAfter(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();

        //then
        assertTrue(eventYoungerThanOneMinute > 0);

        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    @Test
    public void givenThreadsProducingEvents_whenGetForEventsOlderThanOneMinute_thenReturnThoseEventsInTheLockFreeWay() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        EventWindowSort eventWindowSort = new EventWindowSort();
        int numberOfThreads = 2;
        //when
        Runnable producer = () -> IntStream
          .rangeClosed(0, 100)
          .forEach(index -> eventWindowSort.acceptEvent(new Event(ZonedDateTime
            .now()
            .minusSeconds(index), UUID
            .randomUUID()
            .toString())));

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(producer);
        }

        Thread.sleep(500);

        ConcurrentNavigableMap<ZonedDateTime, String> eventsFromLastMinute = eventWindowSort.getEventsOlderThatOneMinute();

        long eventsOlderThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isBefore(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();
        assertTrue(eventsOlderThanOneMinute > 0);

        long eventYoungerThanOneMinute = eventsFromLastMinute
          .entrySet()
          .stream()
          .filter(e -> e
            .getKey()
            .isAfter(ZonedDateTime
              .now()
              .minusMinutes(1)))
          .count();

        //then
        assertEquals(eventYoungerThanOneMinute, 0);

        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    class EventWindowSort {
        private final ConcurrentSkipListMap<ZonedDateTime, String> events
                = new ConcurrentSkipListMap<>(Comparator.comparingLong(value -> value.toInstant().toEpochMilli()));

        void acceptEvent(Event event) {
            events.put(event.getEventTime(), event.getContent());
        }

        ConcurrentNavigableMap<ZonedDateTime, String> getEventsFromLastMinute() {
            return events.tailMap(ZonedDateTime.now().minusMinutes(1));
        }

        ConcurrentNavigableMap<ZonedDateTime, String> getEventsOlderThatOneMinute() {
            return events.headMap(ZonedDateTime.now().minusMinutes(1));
        }
    }

    class Event {
        private final ZonedDateTime eventTime;
        private final String content;

        Event(ZonedDateTime eventTime, String content) {
            this.eventTime = eventTime;
            this.content = content;
        }

        ZonedDateTime getEventTime() {
            return eventTime;
        }

        String getContent() {
            return content;
        }
    }
}