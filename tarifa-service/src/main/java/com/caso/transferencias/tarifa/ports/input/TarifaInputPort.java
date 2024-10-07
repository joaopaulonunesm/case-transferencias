package com.caso.transferencias.tarifa.ports.input;

import com.caso.transferencias.tarifa.ports.dtos.TarifaTransferenciaDTOPort;

public interface TarifaInputPort {

    void tarifarTransferencia(TarifaTransferenciaDTOPort tarifaTransferenciaDTOPort);
}
