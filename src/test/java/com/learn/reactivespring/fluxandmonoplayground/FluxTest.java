package com.learn.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

  @Test
  public void testFluxWithSampleData() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Reactive");
    stringFlux
        .subscribe(System.out::println);
  }

  @Test
  public void testFluxWithErrorAndData() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Reactive")
        .concatWith(Flux.error(new RuntimeException("Error occured!!")));
    stringFlux
        .subscribe(System.out::println, (e) -> System.out.println("Exception ==> " + e));
  }
  @Test
  public void testFluxWithErrorAndDataWithEnableLog() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Reactive")
        .concatWith(Flux.error(new RuntimeException("Error occured!!")))
        .log();
    stringFlux
        .subscribe(System.out::println, (e) -> System.out.println("Exception ==> " + e));
  }

  @Test
  public void testFluxWithErrorAndDataAfterError() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Reactive")
        .concatWith(Flux.error(new RuntimeException("Error occured!!")))
        .concatWith(Flux.just("After Error!"))
        .log();
    stringFlux
        .subscribe(System.out::println, (e) -> System.out.println("Exception ==> " + e));
  }
  @Test
  public void testFluxWithoutErrorAndDataWithLog() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Reactive")
       /* .concatWith(Flux.error(new RuntimeException("Error occured!!")))*/
        .concatWith(Flux.just("No Error!"))
        .log();
    stringFlux
        .subscribe(System.out::println, (e) -> System.out.println("Exception ==> " + e));
  }
  @Test
  public void testFluxWithOnCompleteOptionInSubscribe() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Reactive")
        .concatWith(Flux.just("No Error!"))
        .log();
    stringFlux
        .subscribe(System.out::println, (e) -> System.out.println("Exception ==> " + e),
            ()->{System.out.println("Completed");});
  }

  @Test
  public void testFluxElements_WithoutError(){
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Reactive")
        .log();
    StepVerifier.create(stringFlux)
        .expectNext("Spring")
        .expectNext("Spring Boot")
        .expectNext("Spring Reactive")
        .verifyComplete();
  }
  @Test
  public void testFluxElements_WithoutError_1(){
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Reactive")
        .log();
    StepVerifier.create(stringFlux)
        .expectNext("Spring","Spring Boot","Spring Reactive")
        .verifyComplete();
  }

  //Below Test case will fail as actual event is onError
  //@Test
  public void testFluxElements_WithError_Fail(){
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Reactive")
        .concatWith(Flux.error(new RuntimeException("Error occured!!")))
        .log();

    StepVerifier.create(stringFlux)
        .expectNext("Spring","Spring Boot","Spring Reactive")
        .verifyComplete();
  }

  @Test
  public void testFluxElements_WithError(){
    Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Spring Reactive")
        .concatWith(Flux.error(new RuntimeException("Error occured!!")))
        .log();

    StepVerifier.create(stringFlux)
        .expectNext("Spring","Spring Boot","Spring Reactive")
        .expectError(RuntimeException.class)
        .verify();
  }

}
