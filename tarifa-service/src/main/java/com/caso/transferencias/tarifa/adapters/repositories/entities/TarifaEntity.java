package com.caso.transferencias.tarifa.adapters.repositories.entities;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.math.BigDecimal;

@DynamoDbBean
@Setter
@NoArgsConstructor
@ToString
public class TarifaEntity {

    public static final String PREFIXO_CHAVE_PARTICAO = "TRANSACAO#";
    public static final String PREFIXO_CHAVE_FILTRO = "TARIFA#";

    private String codigoChaveParticao;
    private String codigoChaveFiltro;
    private BigDecimal valorTarifa;

    public TarifaEntity(String codigoChaveParticao, String codigoChaveFiltro, BigDecimal valorTarifa) {
        this.codigoChaveParticao = PREFIXO_CHAVE_PARTICAO.concat(codigoChaveParticao);
        this.codigoChaveFiltro = PREFIXO_CHAVE_FILTRO.concat(codigoChaveFiltro);
        this.valorTarifa = valorTarifa;
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

    @DynamoDbAttribute("valor_tarifa")
    public BigDecimal getValorTarifa() {
        return valorTarifa;
    }
}
