package com.caso.transferencias.tarifa.domains.calculotarifa;

import java.math.BigDecimal;

public class CalculoValorTarifaPixStrategy implements CalculoValorTarifaStrategy {

    @Override
    public BigDecimal calcular(BigDecimal valorTransferencia) {
        return BigDecimal.ZERO;
    }
}
