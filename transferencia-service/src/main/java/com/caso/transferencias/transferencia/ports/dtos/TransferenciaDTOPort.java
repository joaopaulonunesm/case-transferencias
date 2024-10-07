package com.caso.transferencias.transferencia.ports.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@ToString
public class TransferenciaDTOPort {

    private String id;
    private String tipo;
    private BigDecimal valor;
    private String idContaOrigem;
    private String idContaDestino;

    public TransferenciaDTOPort(String tipo, BigDecimal valor, String idContaOrigem, String idContaDestino) {
        this.tipo = tipo;
        this.valor = valor;
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
    }
}