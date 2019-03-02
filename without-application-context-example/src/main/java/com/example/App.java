package com.example;

import java.time.Duration;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class App {

    public static void main(final String[] args) {

        final HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFunction());

        HttpServer.create()
                .host("0.0.0.0")
                .port(8080)
                .handle(new ReactorHttpHandlerAdapter(httpHandler))
                .bindUntilJavaShutdown(Duration.ofMinutes(1), server -> {
                });
    }

    static RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/hello", App::count)
                .build();
    }

    static Mono<ServerResponse> count(final ServerRequest request) {
        return ServerResponse.ok().syncBody("Hello, world!");
    }
}
