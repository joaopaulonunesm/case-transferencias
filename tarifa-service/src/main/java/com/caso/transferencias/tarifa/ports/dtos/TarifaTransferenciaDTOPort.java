package com.caso.transferencias.tarifa.ports.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@ToString
public class TarifaTransferenciaDTOPort {

    private String idTransferencia;
    private String tipoTransferencia;
    private BigDecimal valor;
    private String idContaOrigem;
    private String idContaDestino;
}