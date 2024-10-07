package com.caso.transferencias.tarifa.domains;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@ToString
public class Tarifa {

    private String id;
    private String idTransferencia;
    private BigDecimal valorTransferencia;
    private BigDecimal valorTarifa;

    public Tarifa(String idTransferencia, BigDecimal valorTransferencia) {
        this.id = UUID.randomUUID().toString();
        this.idTransferencia = idTransferencia;
        this.valorTransferencia = valorTransferencia;
    }

    public void calcularValorTarifa(TipoTransferencia tipoTransferencia) {
        CalculadoraValorTarifa calculadoraValorTarifa = new CalculadoraValorTarifa();
        this.valorTarifa = calculadoraValorTarifa.calcularTarifa(tipoTransferencia, this.valorTransferencia);
    }
}
