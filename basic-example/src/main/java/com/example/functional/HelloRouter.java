package com.example.functional;

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
        return route()
                .GET("/fn/hello", handler::getHello)
                .POST("/fn/hello", handler::postHello)
                .build();
    }

    @Bean
    public HelloHandler handler() {
        return new HelloHandler();
    }
}
