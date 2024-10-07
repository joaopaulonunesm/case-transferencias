package com.caso.transferencias.transferencia.adapters.apis.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@ToString
public class TransferenciaResponse {

    @JsonProperty("id_transferencia")
    private String id;

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("valor")
    private BigDecimal valor;

    @JsonProperty("id_conta_origem")
    private String idContaOrigem;

    @JsonProperty("id_conta_destino")
    private String idContaDestino;
}
