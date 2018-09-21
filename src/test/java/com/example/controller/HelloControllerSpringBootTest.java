package com.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class HelloControllerSpringBootTest {

    @Autowired
    private ApplicationContext applicationContext;
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient
                .bindToApplicationContext(applicationContext)
                .build();
    }

    @Test
    void getHello() {
        client.get().uri("/hello").exchange()
                .expectBody(String.class)
                .isEqualTo("Hello World");
    }
}
