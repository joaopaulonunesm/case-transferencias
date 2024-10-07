package com.caso.transferencias.transferencia.ports.input;

import com.caso.transferencias.transferencia.ports.dtos.TransferenciaDTOPort;
import reactor.core.publisher.Flux;

public interface TransferenciaInputPort {

    Flux<TransferenciaDTOPort> buscarPorContaOrigem(String idContaOrigem);

    TransferenciaDTOPort transferir(TransferenciaDTOPort transferenciaDTOPort);
}
