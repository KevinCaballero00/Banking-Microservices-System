package com.banking.transactionservice.infrastructure.adapter.out.messaging;

import com.banking.transactionservice.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionEventPublisher {

    private static final String TOPIC = "transaction-completed";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishTransactionCompleted(Transaction transaction) {
        try {
            String message = String.format(
                    "{\"reference\":\"%s\",\"source\":\"%s\",\"target\":\"%s\",\"amount\":%s,\"status\":\"%s\"}",
                    transaction.getTransactionReference(),
                    transaction.getSourceAccountNumber(),
                    transaction.getTargetAccountNumber(),
                    transaction.getAmount(),
                    transaction.getStatus()
            );
            kafkaTemplate.send(TOPIC, transaction.getTransactionReference(), message);
            log.info("Transaction event published: {}", transaction.getTransactionReference());
        } catch (Exception e) {
            // Kafka no está corriendo — la transferencia igual se completa
            log.warn("Could not publish transaction event (Kafka unavailable): {}", e.getMessage());
        }
    }
}
