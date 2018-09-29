package com.example.functional;

import static org.springframework.http.MediaType.*;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.data.Hello;
import com.example.data.YourName;

import reactor.core.publisher.Mono;

/**
 * {@link HelloRouter}によって{@link RouterFunction}登録されるハンドラー。
 *
 */
public class HelloHandler {

    /**
     * GETリクエストを捌くハンドラーの例。
     * 
     * @param request
     * @return
     */
    public Mono<ServerResponse> getHello(final ServerRequest request) {
        return ServerResponse.ok().contentType(TEXT_PLAIN)
                .syncBody("Hello Functional World");
    }

    /**
     * POSTリクエストを捌くハンドラーの例。
     * 
     * @param request
     * @return
     */
    public Mono<ServerResponse> postHello(final ServerRequest request) {
        final var yourName = request.bodyToMono(YourName.class);
        final var hello = yourName
                .map(YourName::getName)
                .map(name -> "HELLO " + name)
                .map(Hello::new);
        return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(hello, Hello.class);
    }
}
