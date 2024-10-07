package com.caso.transferencias.transferencia.adapters.apis.rest.interceptors;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.caso.transferencias.transferencia.adapters.Constantes.CORRELATION_ID;

@Component
public class CorrelationIdInterceptor implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String correlationId = exchange.getRequest().getHeaders()
                .getOrEmpty(CORRELATION_ID)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Header CorrelationId é obrigatório!"));

        MDC.put(CORRELATION_ID, correlationId);

        return chain.filter(exchange);
    }
}
