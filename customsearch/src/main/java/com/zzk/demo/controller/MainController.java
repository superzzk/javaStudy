package com.zzk.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
public class MainController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("s")
    public String s() {
        System.out.println("s");

        RestClient customClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
//                .messageConverters(converters -> converters.add(new MyCustomMessageConverter()))
                .baseUrl("https://www.google.com")
                .defaultUriVariables(Map.of("variable", "foo"))
                .defaultHeader("My-Header", "Foo")
//                .requestInterceptor(myCustomInterceptor)
//                .requestInitializer(myCustomInitializer)
                .build();

        ResponseEntity<String> result = customClient.get()
//                .uri("https://example.com")
                .retrieve()
                .toEntity(String.class);

        System.out.println("Response status: " + result.getStatusCode());
        System.out.println("Response headers: " + result.getHeaders());
        System.out.println("Contents: " + result.getBody());

        return result.getBody();
    }
}
