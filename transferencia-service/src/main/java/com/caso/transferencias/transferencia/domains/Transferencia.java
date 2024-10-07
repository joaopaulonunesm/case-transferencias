package com.caso.transferencias.transferencia.domains;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@ToString
public class Transferencia {

    private String id;
    private TipoTransferencia tipo;
    private BigDecimal valor;
    private String idContaOrigem;
    private String idContaDestino;

    public Transferencia(String tipo, BigDecimal valor, String idContaOrigem, String idContaDestino) {
        this.id = UUID.randomUUID().toString();
        this.tipo = TipoTransferencia.valueOfCodigo(tipo);
        this.valor = valor;
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
    }
}
