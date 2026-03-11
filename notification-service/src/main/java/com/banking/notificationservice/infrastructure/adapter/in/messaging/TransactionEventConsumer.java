package com.banking.notificationservice.infrastructure.adapter.in.messaging;

import com.banking.notificationservice.domain.model.TransactionEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionEventConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "transaction-completed", groupId = "notification-group")
    public void consume(String message) {
        try {
            TransactionEvent event = objectMapper.readValue(message, TransactionEvent.class);
            log.info("📧 Notification received — Reference: {}, From: {}, To: {}, Amount: {}, Status: {}",
                    event.getReference(),
                    event.getSource(),
                    event.getTarget(),
                    event.getAmount(),
                    event.getStatus());

            // Aquí iría el envío de email cuando configures SMTP
            // emailService.sendTransactionNotification(event);

        } catch (Exception e) {
            log.error("Error processing transaction event: {}", e.getMessage());
        }
    }
}
