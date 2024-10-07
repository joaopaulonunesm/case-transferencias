package com.caso.transferencias.tarifa.adapters.repositories;

import com.caso.transferencias.tarifa.adapters.repositories.entities.TarifaEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Configuration
public class TableConfig {

    @Bean
    public DynamoDbAsyncTable<TarifaEntity> tarifaTable(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        return dynamoDbEnhancedAsyncClient.table("tb_transacao", TableSchema.fromBean(TarifaEntity.class));
    }
}
