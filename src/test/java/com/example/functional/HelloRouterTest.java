package com.example.functional;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringJUnitConfig(HelloRouter.class)
public class HelloRouterTest {

    @Autowired
    private HelloRouter router;
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        final var routerFunction = router.routerFunction();
        client = WebTestClient
                .bindToRouterFunction(routerFunction)
                .build();
    }

    @Test
    void getHello() {
        client.get().uri("/fn/hello").exchange()
                .expectBody(String.class)
                .isEqualTo("Hello Functional World");
    }

    @Test
    void postHello() {
        final var body = Collections.singletonMap("name", "FUNCTIONAL WORLD");
        client.post().uri("/fn/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(body)
                .exchange()
                .expectBody(Map.class)
                .isEqualTo(Collections.singletonMap("message", "HELLO FUNCTIONAL WORLD"));
    }
}
