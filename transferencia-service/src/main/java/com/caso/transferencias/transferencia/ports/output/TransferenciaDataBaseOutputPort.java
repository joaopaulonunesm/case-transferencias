package com.caso.transferencias.transferencia.ports.output;

import com.caso.transferencias.transferencia.ports.dtos.TransferenciaDTOPort;
import reactor.core.publisher.Flux;

public interface TransferenciaDataBaseOutputPort {

    Flux<TransferenciaDTOPort> buscarTodasPorIdContaOrigem(String idContaOrigem);

    TransferenciaDTOPort criar(TransferenciaDTOPort transferenciaDTOPort);
}
