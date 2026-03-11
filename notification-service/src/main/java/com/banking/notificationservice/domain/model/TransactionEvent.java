package com.banking.notificationservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEvent {
    private String reference;
    private String source;
    private String target;
    private BigDecimal amount;
    private String status;
}
