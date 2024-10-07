package com.caso.transferencias.tarifa.adapters.repositories;

import com.caso.transferencias.tarifa.adapters.repositories.entities.TarifaEntity;
import com.caso.transferencias.tarifa.ports.dtos.TarifaDTOPort;
import com.caso.transferencias.tarifa.ports.output.TarifaDataBaseOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;

import static com.caso.transferencias.tarifa.adapters.repositories.entities.TarifaEntity.PREFIXO_CHAVE_FILTRO;
import static com.caso.transferencias.tarifa.adapters.repositories.entities.TarifaEntity.PREFIXO_CHAVE_PARTICAO;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TarifaRepository implements TarifaDataBaseOutputPort {

    private final DynamoDbAsyncTable<TarifaEntity> transferenciaTable;

    @Override
    public TarifaDTOPort salvar(TarifaDTOPort tarifaTransferenciaDTOPort) {
        TarifaEntity entity = toEntity(tarifaTransferenciaDTOPort);
        transferenciaTable.putItem(entity).join();
        return toDto(entity);
    }

    private TarifaDTOPort toDto(TarifaEntity tarifaEntity) {
        return new TarifaDTOPort(
                tarifaEntity.getCodigoChaveFiltro().replace(PREFIXO_CHAVE_FILTRO, ""),
                tarifaEntity.getCodigoChaveParticao().replace(PREFIXO_CHAVE_PARTICAO, ""),
                tarifaEntity.getValorTarifa()
        );
    }

    private TarifaEntity toEntity(TarifaDTOPort tarifaTransferenciaDTOPort) {
        return new TarifaEntity(
                tarifaTransferenciaDTOPort.getIdTransferencia(),
                tarifaTransferenciaDTOPort.getId(),
                tarifaTransferenciaDTOPort.getValorTarifa()
        );
    }
}
