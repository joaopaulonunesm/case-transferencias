package com.caso.transferencias.tarifa.adapters.apis.massage;

import com.caso.transferencias.tarifa.adapters.apis.massage.models.AtributoMessage;
import com.caso.transferencias.tarifa.adapters.apis.massage.models.SqsMessage;
import com.caso.transferencias.tarifa.adapters.apis.massage.models.TransferenciaEventoSqsMessage;
import com.caso.transferencias.tarifa.ports.dtos.TarifaTransferenciaDTOPort;
import com.caso.transferencias.tarifa.ports.input.TarifaInputPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import java.util.Map;

import static com.caso.transferencias.tarifa.adapters.Constantes.CORRELATION_ID;

@Component
@Slf4j
public class TransferenciaEventoListener {

    private String filaTarifasSqs;
    private final SqsClient sqsClient;
    private final TarifaInputPort tarifaInputPort;
    private final ObjectMapper objectMapper;
    private final ReceiveMessageRequest receiveMessageRequest;

    public TransferenciaEventoListener(@Value("${tarifas.fila-sqs-url}") String filaTarifasSqs,
                                       SqsClient sqsClient,
                                       TarifaInputPort tarifaInputPort,
                                       ObjectMapper objectMapper) {
        this.filaTarifasSqs = filaTarifasSqs;
        this.sqsClient = sqsClient;
        this.tarifaInputPort = tarifaInputPort;
        this.objectMapper = objectMapper;
        this.receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(filaTarifasSqs)
                .waitTimeSeconds(1)
                .maxNumberOfMessages(10)
                .build();
    }

    @Scheduled(fixedDelay = 1000)
    public void listener() {
        ReceiveMessageResponse response = sqsClient.receiveMessage(receiveMessageRequest);
        response.messages().forEach(message -> {
            consumirMensagem(message);
            deletarMensagem(message);
        });
    }

    private void consumirMensagem(Message mensagem) {
        try {
            log.info("Consumindo mensagem. mensagem={}", mensagem);

            SqsMessage sqsMessage = objectMapper.readValue(mensagem.body(), SqsMessage.class);
            MDC.put(CORRELATION_ID, getCorrelationDaMensagem(sqsMessage.getMessageAttributes()));

            processarTransferencia(sqsMessage.getMessage());

            log.info("Mensagem consumida com sucesso! mensagem={}", mensagem);
        } catch (Exception exception) {
            log.error("Erro ao consumir mensagem do SQS.", exception);
            throw new RuntimeException("Erro ao consumir mensagem do SQS", exception);
        }
    }

    private void processarTransferencia(String mensagem) throws Exception {
        TransferenciaEventoSqsMessage transferenciaEventoSqsMessage = objectMapper.readValue(mensagem, TransferenciaEventoSqsMessage.class);

        TarifaTransferenciaDTOPort tarifaTransferenciaDTOPort = new TarifaTransferenciaDTOPort(
                transferenciaEventoSqsMessage.getId(),
                transferenciaEventoSqsMessage.getTipo(),
                transferenciaEventoSqsMessage.getValor(),
                transferenciaEventoSqsMessage.getIdContaOrigem(),
                transferenciaEventoSqsMessage.getIdContaDestino()
        );

        tarifaInputPort.tarifarTransferencia(tarifaTransferenciaDTOPort);
    }

    private String getCorrelationDaMensagem(Map<String, AtributoMessage> atributosMensagem) {
        if (!atributosMensagem.containsKey(CORRELATION_ID)) {
            throw new RuntimeException("Falta de CorrelationId na mensagem do SQS");
        }

        return atributosMensagem.get(CORRELATION_ID).getValue();
    }

    private void deletarMensagem(Message message) {
        sqsClient.deleteMessage(builder ->
                builder.queueUrl(filaTarifasSqs)
                        .receiptHandle(message.receiptHandle()));
    }
}
