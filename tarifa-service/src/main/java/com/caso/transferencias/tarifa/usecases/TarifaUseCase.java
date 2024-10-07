package com.caso.transferencias.tarifa.usecases;

import com.caso.transferencias.tarifa.domains.TipoTransferencia;
import com.caso.transferencias.tarifa.ports.dtos.TarifaDTOPort;
import com.caso.transferencias.tarifa.ports.input.TarifaInputPort;
import com.caso.transferencias.tarifa.domains.Tarifa;
import com.caso.transferencias.tarifa.ports.dtos.TarifaTransferenciaDTOPort;
import com.caso.transferencias.tarifa.ports.output.TarifaDataBaseOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TarifaUseCase implements TarifaInputPort {

    private final TarifaDataBaseOutputPort tarifaDataBaseOutputPort;

    @Override
    public void tarifarTransferencia(TarifaTransferenciaDTOPort tarifaTransferenciaDTOPort) {
        log.info("Realizando transferencia entre contas. transferenciaDTOPort={}", tarifaTransferenciaDTOPort);

        TipoTransferencia tipoTransferencia;

        try {
            tipoTransferencia = TipoTransferencia.valueOfCodigo(tarifaTransferenciaDTOPort.getTipoTransferencia());
        } catch (IllegalArgumentException exception) {
            log.warn("Transferência possui um tipo que não é tarifável. tarifaTransferenciaDTOPort={}", tarifaTransferenciaDTOPort);
            return;
        }

        Tarifa tarifa = criarTarifa(tarifaTransferenciaDTOPort);

        tarifa.calcularValorTarifa(tipoTransferencia);

        tarifaDataBaseOutputPort.salvar(domainToPort(tarifa));

        log.info("Transferencia realizada com sucesso! transferenciaDTOPort={}", tarifaTransferenciaDTOPort);
    }

    private Tarifa criarTarifa(TarifaTransferenciaDTOPort tarifaTransferenciaDTOPort) {
        return new Tarifa(
                tarifaTransferenciaDTOPort.getIdTransferencia(),
                tarifaTransferenciaDTOPort.getValor()
        );
    }

    private TarifaDTOPort domainToPort(Tarifa tarifa) {
        return new TarifaDTOPort(
                tarifa.getId(),
                tarifa.getIdTransferencia(),
                tarifa.getValorTarifa()
        );
    }
}
