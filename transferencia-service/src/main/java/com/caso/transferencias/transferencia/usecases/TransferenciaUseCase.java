package com.caso.transferencias.transferencia.usecases;

import com.caso.transferencias.transferencia.domains.Transferencia;
import com.caso.transferencias.transferencia.ports.dtos.TransferenciaDTOPort;
import com.caso.transferencias.transferencia.ports.input.TransferenciaInputPort;
import com.caso.transferencias.transferencia.ports.output.NotificadorTransferenciaOutputPort;
import com.caso.transferencias.transferencia.ports.output.TransferenciaDataBaseOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransferenciaUseCase implements TransferenciaInputPort {

    private final TransferenciaDataBaseOutputPort transferenciaDataBaseOutputPort;
    private final NotificadorTransferenciaOutputPort notificadorTransferenciaOutputPort;

    @Override
    public Flux<TransferenciaDTOPort> buscarPorContaOrigem(String idContaOrigem) {
        log.info("Consultando transferencias por idContaOrigem={}", idContaOrigem);

        Flux<TransferenciaDTOPort> transferencias = transferenciaDataBaseOutputPort.buscarTodasPorIdContaOrigem(idContaOrigem);

        log.info("Transferencias por idContaOrigem consultadas com sucesso! idContaOrigem={}", idContaOrigem);
        return transferencias;
    }

    @Override
    public TransferenciaDTOPort transferir(TransferenciaDTOPort transferenciaDTOPort) {
        log.info("Realizando transferencia entre contas. transferenciaDTOPort={}", transferenciaDTOPort);

        Transferencia transferencia = portToDomain(transferenciaDTOPort);

        transferenciaDTOPort = transferenciaDataBaseOutputPort.criar(domainToPort(transferencia));

        notificadorTransferenciaOutputPort.notificar(transferenciaDTOPort);

        log.info("Transferencia realizada com sucesso! transferenciaDTOPort={}", transferenciaDTOPort);
        return transferenciaDTOPort;
    }

    private Transferencia portToDomain(TransferenciaDTOPort transferenciaDTOPort) {
        return new Transferencia(
                transferenciaDTOPort.getTipo(),
                transferenciaDTOPort.getValor(),
                transferenciaDTOPort.getIdContaOrigem(),
                transferenciaDTOPort.getIdContaDestino()
        );
    }

    private TransferenciaDTOPort domainToPort(Transferencia transferencia) {
        return new TransferenciaDTOPort(
                transferencia.getId(),
                transferencia.getTipo().getCodigo(),
                transferencia.getValor(),
                transferencia.getIdContaOrigem(),
                transferencia.getIdContaDestino()
        );
    }
}
