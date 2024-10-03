package com.zzk.demo;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChatTest {

    @Autowired
    ChatClient chatClient;

    @Test
    void a(){
        String res = chatClient.prompt()
                .user("hello")
                .call()
                .content();
        System.out.println(res);
    }
}
