package com.caso.transferencias.transferencia.adapters.repositories;

import com.caso.transferencias.transferencia.adapters.repositories.entities.TransferenciaEntity;
import com.caso.transferencias.transferencia.ports.dtos.TransferenciaDTOPort;
import com.caso.transferencias.transferencia.ports.output.TransferenciaDataBaseOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import static com.caso.transferencias.transferencia.adapters.repositories.entities.TransferenciaEntity.PREFIXO_CHAVE_FILTRO;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TransferenciaRepository implements TransferenciaDataBaseOutputPort {

    private final DynamoDbAsyncTable<TransferenciaEntity> transferenciaTable;

    @Override
    public Flux<TransferenciaDTOPort> buscarTodasPorIdContaOrigem(String idContaOrigem) {
        QueryEnhancedRequest query = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(builder -> builder.partitionValue(idContaOrigem)))
                .build();

        SdkPublisher<Page<TransferenciaEntity>> transferenciasPaginada = transferenciaTable
                .index(TransferenciaEntity.GSI_TRANSFERENCIA_CONTA_ORIGEM)
                .query(query);

        return Flux.create(sink -> {
            transferenciasPaginada.subscribe(new Subscriber<>() {
                @Override
                public void onSubscribe(Subscription subscription) {
                    subscription.request(Long.MAX_VALUE);
                }

                @Override
                public void onNext(Page<TransferenciaEntity> transferenciaEntityPage) {
                    transferenciaEntityPage.items().forEach(transferenciaEntity -> {
                        TransferenciaDTOPort dto = toDto(transferenciaEntity);
                        sink.next(dto);
                    });
                }

                @Override
                public void onError(Throwable throwable) {
                    sink.error(throwable);
                }

                @Override
                public void onComplete() {
                    sink.complete();
                }
            });
        });
    }

    @Override
    public TransferenciaDTOPort criar(TransferenciaDTOPort transferenciaDTOPort) {
        TransferenciaEntity entity = toEntity(transferenciaDTOPort);
        transferenciaTable.putItem(entity).join();
        return toDto(entity);
    }

    private TransferenciaDTOPort toDto(TransferenciaEntity transferenciaEntity) {
        return new TransferenciaDTOPort(
                transferenciaEntity.getCodigoChaveFiltro().replace(PREFIXO_CHAVE_FILTRO, ""),
                transferenciaEntity.getTipo(),
                transferenciaEntity.getValor(),
                transferenciaEntity.getIdContaOrigem(),
                transferenciaEntity.getIdContaDestino()
        );
    }

    private TransferenciaEntity toEntity(TransferenciaDTOPort transferenciaDTOPort) {
        return new TransferenciaEntity(
                transferenciaDTOPort.getId(),
                transferenciaDTOPort.getId(),
                transferenciaDTOPort.getTipo(),
                transferenciaDTOPort.getValor(),
                transferenciaDTOPort.getIdContaOrigem(),
                transferenciaDTOPort.getIdContaDestino()
        );
    }
}
