package com.zzk.study.guava.basic;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class GuavaEventBusUnitTest {

    private EventListener listener;
    private EventBus eventBus;

    @Before
    public void setUp() {
        eventBus = new EventBus();
        listener = new EventListener();

        eventBus.register(listener);
    }

    @After
    public void tearDown() {
        eventBus.unregister(listener);
    }

    @Test
    public void givenStringEvent_whenEventHandled_thenSuccess() {
        listener.resetEventsHandled();

        eventBus.post("String Event");
        assertEquals(1, listener.getEventsHandled());
    }

    @Test
    public void givenCustomEvent_whenEventHandled_thenSuccess() {
        listener.resetEventsHandled();

        CustomEvent customEvent = new CustomEvent("Custom Event");
        eventBus.post(customEvent);

        assertEquals(1, listener.getEventsHandled());
    }

    @Test
    public void givenUnSubscribedEvent_whenEventHandledByDeadEvent_thenSuccess() {
        listener.resetEventsHandled();

        eventBus.post(12345);
        assertEquals(1, listener.getEventsHandled());
    }

    public static class CustomEvent {
        private String action;

        CustomEvent(String action) {
            this.action = action;
        }

        String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }

    public static class EventListener {

        private static int eventsHandled;
        private static final Logger LOG = LoggerFactory.getLogger(EventListener.class);

        @Subscribe
        public void stringEvent(String event) {
            LOG.info("do event [" + event + "]");
            eventsHandled++;
        }

        @Subscribe
        public void someCustomEvent(CustomEvent customEvent) {
            LOG.info("do event [" + customEvent.getAction() + "]");
            eventsHandled++;
        }

        @Subscribe
        public void handleDeadEvent(DeadEvent deadEvent) {
            LOG.info("unhandled event [" + deadEvent.getEvent() + "]");
            eventsHandled++;
        }

        int getEventsHandled() {
            return eventsHandled;
        }

        void resetEventsHandled() {
            eventsHandled = 0;
        }
    }
}
