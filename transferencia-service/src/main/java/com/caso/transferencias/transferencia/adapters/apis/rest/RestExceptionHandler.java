package com.caso.transferencias.transferencia.adapters.apis.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<Void> handleException(ServerWebExchange exchange, Exception ex) {
        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        return exchange.getResponse().setComplete();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<Void> handleResponseStatusException(ServerWebExchange exchange, ResponseStatusException ex) {
        exchange.getResponse().setStatusCode(ex.getStatusCode());
        return exchange.getResponse().setComplete();
    }
}
