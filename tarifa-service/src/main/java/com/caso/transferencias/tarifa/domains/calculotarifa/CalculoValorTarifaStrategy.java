package com.caso.transferencias.tarifa.domains.calculotarifa;

import java.math.BigDecimal;

public interface CalculoValorTarifaStrategy {
    BigDecimal calcular(BigDecimal valorTransferencia);
}
