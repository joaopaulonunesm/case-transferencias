package com.caso.transferencias.tarifa.domains.calculotarifa;

import java.math.BigDecimal;

public class CalculoValorTarifaTedStrategy implements CalculoValorTarifaStrategy {

    @Override
    public BigDecimal calcular(BigDecimal valorTransferencia) {
        if (valorTransferencia.compareTo(BigDecimal.valueOf(100)) >= 0) {
            return valorTransferencia.multiply(BigDecimal.valueOf(0.1));
        }

        return BigDecimal.ZERO;
    }
}
