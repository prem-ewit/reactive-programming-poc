package com.learn.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

  @Test
  public void testMono_withLog() {
    Mono<String> stringMono = Mono.just("Spring")
        .log();
    stringMono.subscribe(System.out::println);
  }

  @Test
  public void testMono_withLog_1() {
    Mono<String> stringMono = Mono.just("Spring");
    StepVerifier.create(stringMono.log())
        .expectNext("Spring")
        .verifyComplete();
  }

  @Test
  public void testMono_withError() {
   // Mono<String> stringMono = Mono.just("Spring");
    StepVerifier.create(Mono.error(new RuntimeException("Exception occured!!!")))
        .expectError(RuntimeException.class)
        .verify();
  }
}
