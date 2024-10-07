package com.caso.transferencias.transferencia.adapters.apis.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@ToString
public class TransferenciaRequest {

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("valor")
    private BigDecimal valor;

    @JsonProperty("id_conta_origem")
    private String idContaOrigem;

    @JsonProperty("id_conta_destino")
    private String idContaDestino;
}
