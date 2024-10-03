package com.zzk.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatClient chatClient;
    private final ChatClient chatClientPirate;
    private final ChatClient chatClientWithParam;

    public ChatController(ChatClient chatClient, ChatClient chatClientPirate, ChatClient chatClientWithParam) {
        this.chatClient = chatClient;
        this.chatClientPirate = chatClientPirate;
        this.chatClientWithParam = chatClientWithParam;
    }

    @GetMapping
    public Mono<ResponseEntity<String>> generate(@RequestParam(defaultValue = "Tell me to add a proper prompt in a funny way") String prompt) {
        return Mono.just(
                ResponseEntity.ok(chatClient.prompt()
                        .advisors(new SimpleLoggerAdvisor())
                        .user(prompt)
                        .call()
                        .content())
        );
    }

    @GetMapping("/pirate")
    public Map<String, String> completion(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("completion", chatClientPirate.
                prompt().
                user(message).
                call().
                content());
    }

    @GetMapping("/param")
    Map<String, String> completion(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message,
                                   @RequestParam(value = "voice", defaultValue = "teacher") String voice) {
        return Map.of(
                "completion",
                chatClientWithParam.prompt()
                        .system(sp -> sp.param("voice", voice))
                        .user(message)
                        .call()
                        .content());
    }

}