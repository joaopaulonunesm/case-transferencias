package com.caso.transferencias.transferencia.adapters.repositories;

import com.caso.transferencias.transferencia.adapters.repositories.entities.TransferenciaEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Configuration
public class TableConfig {

    @Bean
    public DynamoDbAsyncTable<TransferenciaEntity> transferenciaTable(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        return dynamoDbEnhancedAsyncClient.table("tb_transacao", TableSchema.fromBean(TransferenciaEntity.class));
    }
}
