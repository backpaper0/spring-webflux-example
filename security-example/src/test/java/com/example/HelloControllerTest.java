package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class HelloControllerTest {

    @Autowired
    private ApplicationContext applicationContext;
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        this.client = WebTestClient
                .bindToApplicationContext(applicationContext)
                .apply(SecurityMockServerConfigurers.springSecurity())
                .configureClient()
                .filter(ExchangeFilterFunctions.basicAuthentication())
                .build();
    }

    @Test
    void helloUnauthorized() {
        client
                .get().uri("/hello")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void helloOk() {
        client
                .mutateWith(SecurityMockServerConfigurers.mockUser("example").roles("USER"))
                .get().uri("/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello World");
    }

    @Test
    void foobarUnauthorized() {
        client
                .get().uri("/foobar")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void foobarForbidden() {
        client
                .mutateWith(SecurityMockServerConfigurers.mockUser("example").roles("USER"))
                .get().uri("/foobar")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void foobarOk() {
        client
                .mutateWith(SecurityMockServerConfigurers.mockUser("example").roles("FOOBAR"))
                .get().uri("/foobar")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello FooBar(example)");
    }

    @Test
    void hogeUnauthorized() {
        client
                .get().uri("/hoge")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void hogeForbidden() {
        client
                .mutateWith(SecurityMockServerConfigurers.mockUser("example").roles("FOOBAR"))
                .get().uri("/hoge")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void hogeOk() {
        client
                .mutateWith(SecurityMockServerConfigurers.mockUser("example").roles("HOGE"))
                .get().uri("/hoge")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello Method Security");
    }
}
