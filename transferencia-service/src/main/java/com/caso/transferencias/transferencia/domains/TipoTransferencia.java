package com.caso.transferencias.transferencia.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum TipoTransferencia {

    PIX("PIX"), TED("TED");

    private String codigo;

    public static TipoTransferencia valueOfCodigo(String codigo) {
        return Arrays.stream(values())
                .filter(tipoTransferencia -> tipoTransferencia.codigo.equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de Transferência não processável!"));
    }
}
