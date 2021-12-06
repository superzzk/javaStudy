package zzk.study.java.core.util.concurrent.thread.thread_local;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadLocalIntegrationTest {
    @Test
    public void givenThreadThatStoresContextInAMap_whenStartThread_thenShouldSetContextForBothUsers() throws ExecutionException, InterruptedException {
        //when
        SharedMapWithUserContext firstUser = new SharedMapWithUserContext(1);
        SharedMapWithUserContext secondUser = new SharedMapWithUserContext(2);
        new Thread(firstUser).start();
        new Thread(secondUser).start();

        Thread.sleep(3000);
        //then
        assertEquals(SharedMapWithUserContext.userContextPerUserId.size(), 2);
    }

    @Test
    public void givenThreadThatStoresContextInThreadLocal_whenStartThread_thenShouldStoreContextInThreadLocal() throws ExecutionException, InterruptedException {
        //when
        ThreadLocalWithUserContext firstUser = new ThreadLocalWithUserContext(1);
        ThreadLocalWithUserContext secondUser = new ThreadLocalWithUserContext(2);
        new Thread(firstUser).start();
        new Thread(secondUser).start();

        Thread.sleep(3000);
    }

    public static class UserRepository {
        String getUserNameForUserId(Integer userId) {
            return UUID.randomUUID().toString();
        }
    }

    public static class Context {
        private final String userName;

        Context(String userName) {
            this.userName = userName;
        }

        @Override
        public String toString() {
            return "Context{" +
                    "userNameSecret='" + userName + '\'' +
                    '}';
        }
    }

    public static class SharedMapWithUserContext implements Runnable {
        final static Map<Integer, Context> userContextPerUserId = new ConcurrentHashMap<>();
        private final Integer userId;
        private UserRepository userRepository = new UserRepository();

        SharedMapWithUserContext(Integer userId) {
            this.userId = userId;
        }

        @Override
        public void run() {
            String userName = userRepository.getUserNameForUserId(userId);
            userContextPerUserId.put(userId, new Context(userName));
        }
    }

    public static class ThreadLocalWithUserContext implements Runnable {
        private static final Logger LOG = LoggerFactory.getLogger(ThreadLocalWithUserContext.class);

        private static final ThreadLocal<Context> userContext = new ThreadLocal<>();
        private final Integer userId;
        private UserRepository userRepository = new UserRepository();

        ThreadLocalWithUserContext(Integer userId) {
            this.userId = userId;
        }


        @Override
        public void run() {
            String userName = userRepository.getUserNameForUserId(userId);
            userContext.set(new Context(userName));
            LOG.debug("thread context for given userId: " + userId + " is: " + userContext.get());
        }
    }

}
