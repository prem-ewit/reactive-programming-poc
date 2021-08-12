package com.learn.reactivespring.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Class demonstrates different approach to write test and test the end point.
 */
@WebFluxTest
public class FluxControllerTest {

  @Autowired
  WebTestClient webTestClient;

  @Test
  public void testGetFlux_approach_1() {

    Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Integer.class)
        .getResponseBody();

    StepVerifier.create(integerFlux)
        .expectSubscription()
        .expectNext(1, 2, 3, 4, 5, 6)
        .verifyComplete();
  }

  @Test
  public void testGetFlux_approach_2() {

    webTestClient.get().uri("/flux")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Integer.class)
        .hasSize(6);
  }

  @Test
  public void testGetFlux_approach_3() {

    List<Integer> expectedList = Arrays.asList(1, 2, 3, 4, 5, 6);
    EntityExchangeResult<List<Integer>> exchangeResult = webTestClient.get().uri("/flux")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Integer.class)
        .returnResult();
    assertEquals(expectedList, exchangeResult.getResponseBody());
  }

  @Test
  public void testGetFlux_approach_4() {

    List<Integer> expectedList = Arrays.asList(1, 2, 3, 4, 5, 6);
    webTestClient.get().uri("/flux")
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Integer.class)
        .consumeWith((res) -> {
          assertEquals(expectedList, res.getResponseBody());
        });
  }
  //Test cases for infinite streams
  @Test
  public void testGetFlux_streams() {

    Flux<Long> longFlux = webTestClient.get().uri("/flux-stream-infinite")
        .accept(MediaType.APPLICATION_STREAM_JSON)
        .exchange()
        .expectStatus().isOk()
        .returnResult(Long.class)
        .getResponseBody();

    StepVerifier.create(longFlux)
        .expectSubscription()
        .expectNext(0l,1l, 2l, 3l, 4l, 5l, 6l)
        .thenCancel()
        .verify();
  }
}
