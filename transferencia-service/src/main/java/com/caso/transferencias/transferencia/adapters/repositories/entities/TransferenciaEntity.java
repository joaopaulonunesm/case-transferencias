package com.caso.transferencias.transferencia.adapters.repositories.entities;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;

@DynamoDbBean
@Setter
@NoArgsConstructor
@ToString
public class TransferenciaEntity {

    public static final String PREFIXO_CHAVE_PARTICAO = "TRANSACAO#";
    public static final String PREFIXO_CHAVE_FILTRO = "TRANSFERENCIA#";
    public static final String GSI_TRANSFERENCIA_CONTA_ORIGEM = "gsi_transferencia_conta_origem";

    private String codigoChaveParticao;
    private String codigoChaveFiltro;
    private String tipo;
    private BigDecimal valor;
    private String idContaOrigem;
    private String idContaDestino;

    public TransferenciaEntity(String codigoChaveParticao, String codigoChaveFiltro, String tipo, BigDecimal valor, String idContaOrigem, String idContaDestino) {
        this.codigoChaveParticao = PREFIXO_CHAVE_PARTICAO.concat(codigoChaveParticao);
        this.codigoChaveFiltro = PREFIXO_CHAVE_FILTRO.concat(codigoChaveFiltro);
        this.tipo = tipo;
        this.valor = valor;
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("codigo_chave_particao")
    public String getCodigoChaveParticao() {
        return codigoChaveParticao;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("codigo_chave_filtro")
    public String getCodigoChaveFiltro() {
        return codigoChaveFiltro;
    }

    @DynamoDbAttribute("tipo")
    public String getTipo() {
        return tipo;
    }

    @DynamoDbAttribute("valor")
    public BigDecimal getValor() {
        return valor;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = GSI_TRANSFERENCIA_CONTA_ORIGEM)
    @DynamoDbAttribute("id_conta_origem")
    public String getIdContaOrigem() {
        return idContaOrigem;
    }

    @DynamoDbAttribute("id_conta_destino")
    public String getIdContaDestino() {
        return idContaDestino;
    }
}
