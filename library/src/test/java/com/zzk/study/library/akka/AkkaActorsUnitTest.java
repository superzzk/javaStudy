package com.zzk.study.library.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.testkit.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static akka.pattern.PatternsCS.ask;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AkkaActorsUnitTest {

    private static ActorSystem system = null;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("test-system");
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system, Duration.apply(1000, TimeUnit.MILLISECONDS), true);
        system = null;
    }

    @Test
    public void givenAnActor_sendHimAMessageUsingTell() {

        final TestKit probe = new TestKit(system);
        ActorRef myActorRef = probe.childActorOf(Props.create(MyActor.class));
        myActorRef.tell("printit", probe.testActor());

        probe.expectMsg("Got Message");
    }

    @Test
    public void givenAnActor_sendHimAMessageUsingAsk() throws ExecutionException, InterruptedException {

        final TestKit probe = new TestKit(system);
        ActorRef wordCounterActorRef = probe.childActorOf(Props.create(WordCounterActor.class));

        CompletableFuture<Object> future =
                ask(wordCounterActorRef, new WordCounterActor.CountWords("this is a text"), 1000).toCompletableFuture();

        Integer numberOfWords = (Integer) future.get();
        assertTrue("The actor should count 4 words", 4 == numberOfWords);
    }

    @Test
    public void givenAnActor_whenTheMessageIsNull_respondWithException() {
        final TestKit probe = new TestKit(system);
        ActorRef wordCounterActorRef = probe.childActorOf(Props.create(WordCounterActor.class));

        CompletableFuture<Object> future =
                ask(wordCounterActorRef, new WordCounterActor.CountWords(null), 1000).toCompletableFuture();

        try {
            future.get(1000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            assertTrue("Invalid error message", e.getMessage().contains("The text to process can't be null!"));
        } catch (InterruptedException | TimeoutException e) {
            fail("Actor should respond with an exception instead of timing out !");
        }
    }
    
    @Test
    public void givenAnAkkaSystem_countTheWordsInAText() {
    	ActorSystem system = ActorSystem.create("test-system");
        ActorRef myActorRef = system.actorOf(Props.create(MyActor.class), "my-actor");
        myActorRef.tell("printit", null);
//        system.stop(myActorRef);
//        myActorRef.tell(PoisonPill.getInstance(), ActorRef.noSender());
//        myActorRef.tell(Kill.getInstance(), ActorRef.noSender());

        ActorRef readingActorRef = system.actorOf(ReadingActor.props(TEXT), "readingActor");
        readingActorRef.tell(new ReadingActor.ReadLines(), ActorRef.noSender());    //ActorRef.noSender() means the sender ref is akka://test-system/deadLetters

//        Future<Terminated> terminateResponse = system.terminate();
    }
    
    private static String TEXT = "Lorem Ipsum is simply dummy text\n" +
            "of the printing and typesetting industry.\n" +
            "Lorem Ipsum has been the industry's standard dummy text\n" +
            "ever since the 1500s, when an unknown printer took a galley\n" +
            "of type and scrambled it to make a type specimen book.\n" +
            " It has survived not only five centuries, but also the leap\n" +
            "into electronic typesetting, remaining essentially unchanged.\n" +
            " It was popularised in the 1960s with the release of Letraset\n" +
            " sheets containing Lorem Ipsum passages, and more recently with\n" +
            " desktop publishing software like Aldus PageMaker including\n" +
            "versions of Lorem Ipsum.";

    public class MyActor extends AbstractActor {

        private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

        @Override
        public void postStop() {
            log.info("Stopping actor {}", this);
        }

        public Receive createReceive() {
            return receiveBuilder()
                    .matchEquals("printit", p -> {
                        System.out.println("The address of this actor is: " + getSelf());
                        getSender().tell("Got Message", getSelf());
                    })
                    .build();
        }
    }

    public static class FirstActor extends AbstractActor {

        private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

        public static Props props() {
            return Props.create(FirstActor.class);
        }

        @Override
        public void preStart() {
            log.info("Actor started");
        }

        @Override
        public void postStop() {
            log.info("Actor stopped");
        }

        // Messages will not be handled
        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .build();
        }
    }

    public static class PrinterActor extends AbstractActor {

        private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

        public static Props props(String text) {
            return Props.create(PrinterActor.class, text);
        }

        public static final class PrintFinalResult {
            Integer totalNumberOfWords;

            public PrintFinalResult(Integer totalNumberOfWords) {
                this.totalNumberOfWords = totalNumberOfWords;
            }
        }

        @Override
        public void preStart() {
            log.info("Starting PrinterActor {}", this);
        }

        @Override
        public void postStop() {
            log.info("Stopping PrinterActor {}", this);
        }


        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(PrinterActor.PrintFinalResult.class,
                            r -> {
                                log.info("Received PrintFinalResult message from " + getSender());
                                log.info("The text has a total number of {} words", r.totalNumberOfWords);
                            })
                    .build();
        }
    }

    public static class ReadingActor extends AbstractActor {

        private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

        private String text;

        public ReadingActor(String text) {
            this.text = text;
        }

        public static Props props(String text) {
            return Props.create(ReadingActor.class, text);
        }

        public static final class ReadLines {
        }

        @Override
        public void preStart() {
            log.info("Starting ReadingActor {}", this);
        }

        @Override
        public void postStop() {
            log.info("Stopping ReadingActor {}", this);
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(ReadLines.class, r -> {

                        log.info("Received ReadLines message from " + getSender());

                        String[] lines = text.split("\n");
                        List<CompletableFuture> futures = new ArrayList<>();

                        for (int i = 0; i < lines.length; i++) {
                            String line = lines[i];
                            ActorRef wordCounterActorRef = getContext().actorOf(Props.create(WordCounterActor.class), "word-counter-" + i);

                            CompletableFuture<Object> future =
                                    ask(wordCounterActorRef, new WordCounterActor.CountWords(line), 1000).toCompletableFuture();
                            futures.add(future);
                        }

                        Integer totalNumberOfWords = futures.stream()
                                .map(CompletableFuture::join)
                                .mapToInt(n -> (Integer) n)
                                .sum();

                        ActorRef printerActorRef = getContext().actorOf(Props.create(PrinterActor.class), "Printer-Actor");
                        printerActorRef.forward(new PrinterActor.PrintFinalResult(totalNumberOfWords), getContext());
//                    printerActorRef.tell(new PrinterActor.PrintFinalResult(totalNumberOfWords), getSelf());

                    })
                    .build();
        }
    }

    public static class WordCounterActor extends AbstractActor {

        private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

        public static final class CountWords {
            String line;

            public CountWords(String line) {
                this.line = line;
            }
        }

        @Override
        public void preStart() {
            log.info("Starting WordCounterActor {}", this);
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(CountWords.class, r -> {
                        try {
                            log.info("Received CountWords message from " + getSender());
                            int numberOfWords = countWordsFromLine(r.line);
                            getSender().tell(numberOfWords, getSelf());
                        } catch (Exception ex) {
                            getSender().tell(new akka.actor.Status.Failure(ex), getSelf());
                            throw ex;
                        }
                    })
                    .build();
        }

        private int countWordsFromLine(String line) throws Exception {

            if (line == null) {
                throw new IllegalArgumentException("The text to process can't be null!");
            }

            int numberOfWords = 0;
            String[] words = line.split(" ");
            for (String possibleWord : words) {
                if (possibleWord.trim().length() > 0) {
                    numberOfWords++;
                }
            }
            return numberOfWords;
        }
    }

}
