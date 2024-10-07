package com.caso.transferencias.tarifa.ports.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@ToString
public class TarifaDTOPort {

    private String id;
    private String idTransferencia;
    private BigDecimal valorTarifa;
}