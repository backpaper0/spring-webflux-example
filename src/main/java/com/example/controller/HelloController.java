package com.example.controller;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.Hello;
import com.example.data.YourName;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    /**
     * Monoの例。
     * 
     * @return
     */
    @GetMapping("/hello")
    public Mono<String> getHello() {
        return Mono.just("Hello World");
    }

    /**
     * Monoでない値が最終的にMonoになる例。
     * 
     * @see <a href="https://github.com/spring-projects/spring-framework/blob/v5.0.9.RELEASE/spring-webflux/src/main/java/org/springframework/web/reactive/result/method/annotation/AbstractMessageWriterResultHandler.java#L130">https://github.com/spring-projects/spring-framework/blob/v5.0.9.RELEASE/spring-webflux/src/main/java/org/springframework/web/reactive/result/method/annotation/AbstractMessageWriterResultHandler.java#L130</a>
     * @return
     */
    @GetMapping("/hello-string")
    public String getHelloString() {
        return "Hello World";
    }

    /**
     * ReactiveAdapterでMonoに変換される例。
     * 
     * @see <a href="https://github.com/spring-projects/spring-framework/blob/v5.0.9.RELEASE/spring-webflux/src/main/java/org/springframework/web/reactive/result/method/annotation/AbstractMessageWriterResultHandler.java#L125">https://github.com/spring-projects/spring-framework/blob/v5.0.9.RELEASE/spring-webflux/src/main/java/org/springframework/web/reactive/result/method/annotation/AbstractMessageWriterResultHandler.java#L125</a>
     * @see <a href="https://github.com/spring-projects/spring-framework/blob/v5.0.9.RELEASE/spring-core/src/main/java/org/springframework/core/ReactiveAdapterRegistry.java#L216">https://github.com/spring-projects/spring-framework/blob/v5.0.9.RELEASE/spring-core/src/main/java/org/springframework/core/ReactiveAdapterRegistry.java#L216</a>
     * @return
     */
    @GetMapping("/hello-completable-future")
    public CompletableFuture<String> getHelloCompletableFuture() {
        return CompletableFuture.completedFuture("Hello World");
    }

    /**
     * Fluxの例。
     * 
     * @return
     */
    @GetMapping("/hello-flux")
    public Flux<Hello> getHelloFlux() {
        return Flux.range(1, 5)
                .map(i -> new Hello("Hello World " + i));
    }

    /**
     * 1秒毎に1要素を処理する例。
     * 
     * <pre>
     * curl localhost:8080/hello-stream
     * curl localhost:8080/hello-stream -H "Accept: application/stream+json"
     * curl localhost:8080/hello-stream -H "Accept: text/event-stream"
     * </pre>
     * 
     * @return
     */
    @GetMapping("/hello-stream")
    public Flux<Hello> getHelloStream() {
        return Flux.range(1, 5)
                .map(i -> new Hello("Hello World " + i))
                .delayElements(Duration.ofSeconds(1)); //1秒毎に1要素を処理する
    }

    /**
     * JSONを受け取る例。
     * 
     * @param yourName
     * @return
     */
    @PostMapping("/hello")
    public Mono<Hello> postHello(@RequestBody final YourName yourName) {
        return Mono.just(yourName.getName())
                .map(name -> "Hello " + name)
                .map(Hello::new);
    }
}
