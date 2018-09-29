package com.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.data.Hello;
import com.example.data.YourName;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class HelloControllerTest {

    private final WebTestClient client = WebTestClient
            .bindToController(new HelloController())
            .build();

    @Test
    void getHelloWithoutStepVerifier() {
        client.get().uri("/hello").exchange()
                .expectBody(String.class)
                .isEqualTo("Hello World");
    }

    @Test
    void getHello() {
        final Flux<String> body = client.get().uri("/hello").exchange()
                .returnResult(String.class)
                .getResponseBody();

        StepVerifier.create(body)
                .expectNext("Hello World")
                .verifyComplete();
    }

    @Test
    void getHelloFlux() {
        final Flux<Hello> body = client.get().uri("/hello-flux").exchange()
                .returnResult(Hello.class)
                .getResponseBody();

        StepVerifier.create(body)
                .expectNext(new Hello("Hello World 1"))
                .expectNext(new Hello("Hello World 2"))
                .expectNext(new Hello("Hello World 3"))
                .expectNext(new Hello("Hello World 4"))
                .expectNext(new Hello("Hello World 5"))
                .verifyComplete();
    }

    @Test
    void postHello() {

        final Flux<Hello> body = client.post().uri("/hello")
                .contentType(MediaType.APPLICATION_JSON).syncBody(new YourName("Uragami"))
                .exchange()
                .returnResult(Hello.class)
                .getResponseBody();

        StepVerifier.create(body)
                .expectNext(new Hello("Hello Uragami"))
                .verifyComplete();
    }
}
