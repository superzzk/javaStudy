package com.zzk.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void a(){
		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", "127.0.0.1");
		System.setProperty("http.proxyPort", "49373");
		System.setProperty("https.proxyHost", "127.0.0.1");
		System.setProperty("https.proxyPort", "49373");

		RestClient customClient = RestClient.builder()
				.requestFactory(new HttpComponentsClientHttpRequestFactory())
//                .messageConverters(converters -> converters.add(new MyCustomMessageConverter()))
//				.baseUrl("https://www.google.com")
				.baseUrl("https://www.googleapis.com/customsearch/v1?key=AIzaSyCIBlDzwZVpSa9HWBOkWTXbBdnuodvQq3g&cx=a4a0ce8afe5b341e1&q=Immanuel Kant")
//				.defaultUriVariables(Map.of("key", "AIzaSyCIBlDzwZVpSa9HWBOkWTXbBdnuodvQq3g",
//						"cx","a4a0ce8afe5b341e1",
//						"q","Immanuel Kant bio"
//				))
//				.defaultHeader("q", "Immanuel Kant bio")
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
	}

}
