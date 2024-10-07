package com.caso.transferencias.tarifa.domains;

import com.caso.transferencias.tarifa.domains.calculotarifa.CalculoValorTarifaPixStrategy;
import com.caso.transferencias.tarifa.domains.calculotarifa.CalculoValorTarifaStrategy;
import com.caso.transferencias.tarifa.domains.calculotarifa.CalculoValorTarifaTedStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum TipoTransferencia {

    PIX("PIX", new CalculoValorTarifaPixStrategy()),
    TED("TED", new CalculoValorTarifaTedStrategy());

    private String codigo;
    private CalculoValorTarifaStrategy calculoValorTarifaStrategy;

    public static TipoTransferencia valueOfCodigo(String codigo) {
        return Arrays.stream(values())
                .filter(tipoTransferencia -> tipoTransferencia.codigo.equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de Transferência não processável!"));
    }
}
