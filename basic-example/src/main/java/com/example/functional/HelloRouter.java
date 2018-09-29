package com.example.functional;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * {@link RouterFunction}を構築する例。
 *
 */
@Configuration
public class HelloRouter {

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        final var handler = handler();
        return route(GET("/fn/hello"), handler::getHello)
                .andRoute(POST("/fn/hello"), handler::postHello);
    }

    @Bean
    public HelloHandler handler() {
        return new HelloHandler();
    }

    // ApplicationContextを使わずに実行する例。
    // これはNettyで動かしている。

    //    public static void main(final String[] args) throws Exception {
    //        final var hello = new FunctionalHelloHandler();
    //        final var router = new FunctionalHelloRouter(hello).routerFunction();
    //        final var handler = RouterFunctions.toHttpHandler(router);
    //        final var adapter = new ReactorHttpHandlerAdapter(handler);
    //        HttpServer.create("localhost", 8080).newHandler(adapter).block();
    //        Thread.sleep(Long.MAX_VALUE);
    //    }
}
