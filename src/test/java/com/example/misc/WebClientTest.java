package com.example.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.data.Hello;
import com.example.data.YourName;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class WebClientTest {

    @LocalServerPort
    private int port;
    private WebClient client;

    @BeforeEach
    void setUp() {
        final var baseUrl = "http://localhost:" + port;
        client = WebClient.create(baseUrl);
    }

    @Test
    void getHello() {
        final Mono<String> body = client.get().uri("/hello")
                .retrieve().bodyToMono(String.class);

        StepVerifier.create(body)
                .expectNext("Hello World")
                .verifyComplete();
    }

    @Test
    void postHello() {
        final Mono<Hello> body = client.post().uri("/hello")
                .contentType(MediaType.APPLICATION_JSON).syncBody(new YourName("Uragami"))
                .retrieve().bodyToMono(Hello.class);

        StepVerifier.create(body)
                .expectNext(new Hello("Hello Uragami"))
                .verifyComplete();
    }
}
