package com.caso.transferencias.tarifa.ports.output;

import com.caso.transferencias.tarifa.ports.dtos.TarifaDTOPort;

public interface TarifaDataBaseOutputPort {

    TarifaDTOPort salvar(TarifaDTOPort tarifaDTOPort);
}
