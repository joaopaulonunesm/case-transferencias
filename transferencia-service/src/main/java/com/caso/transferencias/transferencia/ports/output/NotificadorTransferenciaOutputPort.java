package com.caso.transferencias.transferencia.ports.output;

import com.caso.transferencias.transferencia.ports.dtos.TransferenciaDTOPort;

public interface NotificadorTransferenciaOutputPort {

    void notificar(TransferenciaDTOPort transferenciaDTOPort);
}
