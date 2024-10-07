package com.caso.transferencias.tarifa.adapters.apis.massage.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TransferenciaEventoSqsMessage {

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
