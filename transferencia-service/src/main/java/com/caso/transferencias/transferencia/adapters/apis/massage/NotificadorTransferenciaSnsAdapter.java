package com.caso.transferencias.transferencia.adapters.apis.massage;

import com.caso.transferencias.transferencia.adapters.Constantes;
import com.caso.transferencias.transferencia.adapters.apis.massage.models.TransferenciaEventoSnsMessage;
import com.caso.transferencias.transferencia.ports.dtos.TransferenciaDTOPort;
import com.caso.transferencias.transferencia.ports.output.NotificadorTransferenciaOutputPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificadorTransferenciaSnsAdapter implements NotificadorTransferenciaOutputPort {

    @Value("${transferencias.topico-sns-arn}")
    private String topicoTransferenciasArn;

    private final SnsClient snsClient;
    private final ObjectMapper objectMapper;

    @Override
    public void notificar(TransferenciaDTOPort transferenciaDTOPort) {
        try {
            TransferenciaEventoSnsMessage transferenciaEventoSnsMessage = new TransferenciaEventoSnsMessage(transferenciaDTOPort.getId(), transferenciaDTOPort.getTipo(), transferenciaDTOPort.getValor(), transferenciaDTOPort.getIdContaOrigem(), transferenciaDTOPort.getIdContaDestino());

            PublishRequest request = PublishRequest.builder()
                    .message(objectMapper.writeValueAsString(transferenciaEventoSnsMessage))
                    .messageAttributes(getHeaders())
                    .topicArn(topicoTransferenciasArn)
                    .build();

            PublishResponse publishResponse = snsClient.publish(request);

            log.info("Transferencia notificada no tópico SNS. publishResponse={}", publishResponse);
        } catch (Exception exception) {
            log.error("Erro ao notificar transferencia no tópico SNS.", exception);
            throw new RuntimeException("Erro ao notificar transferencia no tópico SNS.", exception);
        }
    }

    private Map<String, MessageAttributeValue> getHeaders() {
        String correlationId = MDC.get(Constantes.CORRELATION_ID) == null ? UUID.randomUUID().toString() : MDC.get(Constantes.CORRELATION_ID);
        Map<String, MessageAttributeValue> headers = new HashMap<>();
        headers.put(Constantes.CORRELATION_ID, MessageAttributeValue.builder().dataType("String").stringValue(correlationId).build());
        return headers;
    }
}
