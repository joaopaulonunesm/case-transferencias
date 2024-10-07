package com.caso.transferencias.transferencia.adapters.apis.rest;

import com.caso.transferencias.transferencia.adapters.apis.rest.models.TransferenciaRequest;
import com.caso.transferencias.transferencia.adapters.apis.rest.models.TransferenciaResponse;
import com.caso.transferencias.transferencia.ports.dtos.TransferenciaDTOPort;
import com.caso.transferencias.transferencia.ports.input.TransferenciaInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/transferencias")
@RequiredArgsConstructor
@Slf4j
public class TransferenciaController {

    private final TransferenciaInputPort transferenciaInputPort;

    @GetMapping
    public ResponseEntity<Flux<TransferenciaResponse>> getTransferenciasPorIdContaOrigem(@RequestParam("idContaOrigem") String idContaOrigem) {
        Flux<TransferenciaResponse> transferenciasResponse = transferenciaInputPort.buscarPorContaOrigem(idContaOrigem).map(this::portToResponse);
        return ResponseEntity.ok(transferenciasResponse);
    }

    @PostMapping
    public ResponseEntity<TransferenciaResponse> postTransferencia(@RequestBody TransferenciaRequest transferenciaRequest) {
        TransferenciaDTOPort transferenciaDTOPort = transferenciaInputPort.transferir(requestToPort(transferenciaRequest));
        return ResponseEntity.ok(portToResponse(transferenciaDTOPort));
    }

    private TransferenciaResponse portToResponse(TransferenciaDTOPort transferenciaDTOPort) {
        return new TransferenciaResponse(transferenciaDTOPort.getId(),
                transferenciaDTOPort.getTipo(),
                transferenciaDTOPort.getValor(),
                transferenciaDTOPort.getIdContaOrigem(),
                transferenciaDTOPort.getIdContaDestino());
    }

    private TransferenciaDTOPort requestToPort(TransferenciaRequest transferenciaRequest) {
        return new TransferenciaDTOPort(
                transferenciaRequest.getTipo(),
                transferenciaRequest.getValor(),
                transferenciaRequest.getIdContaOrigem(),
                transferenciaRequest.getIdContaDestino()
        );
    }
}
