package com.example.misc;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Reactorの例。
 *
 */
class ReactorTest {

    @Test
    void just() throws Exception {

        final Mono<String> mono = Mono.just("foobar");
        StepVerifier.create(mono)
                .expectNext("foobar")
                .verifyComplete();

        //        final String t = mono.block();
        //        assertEquals("foobar", t);

        final Flux<String> flux = Flux.just("foo", "bar", "baz");
        StepVerifier.create(flux)
                .expectNext("foo", "bar", "baz")
                .verifyComplete();

        //        final Iterable<String> i = flux.toIterable();
        //        assertIterableEquals(List.of("foo", "bar", "baz"), i);
    }

    @Test
    void empty() throws Exception {

        final Mono<?> mono = Mono.empty();
        StepVerifier.create(mono)
                .verifyComplete();

        final Flux<?> flux = Flux.empty();
        StepVerifier.create(flux)
                .verifyComplete();
    }

    @Test
    void from() throws Exception {

        final Supplier<String> supplier = () -> "foobar";
        final Mono<String> mono = Mono.fromSupplier(supplier);
        StepVerifier.create(mono)
                .expectNext("foobar")
                .verifyComplete();

        final Stream<String> stream = Stream.of("foo", "bar", "baz");
        final Flux<String> flux = Flux.fromStream(stream);
        StepVerifier.create(flux)
                .expectNext("foo", "bar", "baz")
                .verifyComplete();
    }

    @Test
    void zipWith() throws Exception {

        final Mono<String> m1 = Mono.just("foo");
        final Mono<String> m2 = Mono.just("bar");
        final Mono<String> m3 = m1.zipWith(m2, (a, b) -> a + b);
        StepVerifier.create(m3)
                .expectNext("foobar")
                .verifyComplete();

        final Flux<String> f1 = Flux.just("foo", "bar", "baz");
        final Flux<String> f2 = Flux.just("1", "2", "3");
        final Flux<String> f3 = f1.zipWith(f2, String::concat);
        StepVerifier.create(f3)
                .expectNext("foo1", "bar2", "baz3")
                .verifyComplete();
    }
}
