package com.zzk.demo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
//        return new OllamaChatClient(new OllamaApi())
//                .withDefaultOptions(OllamaOptions.create()
//                        .withTemperature(0.9f));
    }

    @Bean
    ChatClient chatClientPirate(ChatClient.Builder builder) {
        return builder.defaultSystem("You are a friendly chat bot that answers question in the voice of a Pirate")
                .build();
    }

    @Bean
    ChatClient chatClientWithParam(ChatClient.Builder builder) {
        return builder.defaultSystem("You are a friendly chat bot that answers question in the voice of a {voice}")
                .build();
    }

}