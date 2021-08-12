package com.learn.reactivespring.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.learn.reactivespring.handler.SampleHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterFunctionConfig {

  @Bean
  public RouterFunction<ServerResponse> route(SampleHandlerFunction handlerFunction){

    return RouterFunctions
          .route(GET("/functional-flux").and(accept(MediaType.APPLICATION_JSON)), handlerFunction::getFlux)
            .andRoute(GET("/functional-mono").and(accept(MediaType.APPLICATION_JSON)), handlerFunction::getMono);
  }

}
