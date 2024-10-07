package com.caso.transferencias.tarifa.domains;

import com.caso.transferencias.tarifa.domains.calculotarifa.CalculoValorTarifaStrategy;

import java.math.BigDecimal;

public class CalculadoraValorTarifa {

    public BigDecimal calcularTarifa(TipoTransferencia tipoTransferencia, BigDecimal valorTransferencia) {
        CalculoValorTarifaStrategy calculoValorTarifaStrategy = tipoTransferencia.getCalculoValorTarifaStrategy();
        return calculoValorTarifaStrategy.calcular(valorTransferencia);
    }
}
