package com.learn.reactivespring.controller;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class FluxController {

  /**
   * Test these end points from Browser or using jnit test cases written in this application
   * @return
   */
  //Sample Get end point to return JSON - Blocking from UI..
  @GetMapping("/flux")
  public Flux<Integer> getFlux() {
    Flux<Integer> integerFlux = Flux.just(1,2,3,4,5,6)
        .log();
    return integerFlux;

  }

  //Sample Get end point to return JSON as stream -
  @GetMapping(value = "/flux-stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Integer> getFluxStream() {
    Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5, 6)
        .delayElements(Duration.ofSeconds(1))
        .log();
    return integerFlux;

  }

  //End point to return infinite stream
  @GetMapping(value = "/flux-stream-infinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Long> getFluxStreamInfinite() {
    Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
        .log();
    return longFlux;
  }
}
