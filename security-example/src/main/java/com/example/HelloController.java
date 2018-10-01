package com.example;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/foobar")
    public Mono<String> foobar(final Mono<Authentication> authentication) {
        return authentication.map(a -> String.format("Hello FooBar(%s)", a.getName()));
    }

    /**
     * 
     * @return
     * @see ReactiveMethodSecurityConfiguration
     */
    @PreAuthorize("hasRole('HOGE')")
    @GetMapping("/hoge")
    public Mono<String> hoge() {
        return Mono.just("Hello Method Security");
    }
}
