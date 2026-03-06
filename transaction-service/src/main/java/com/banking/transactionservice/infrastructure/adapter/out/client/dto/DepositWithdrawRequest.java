package com.banking.transactionservice.infrastructure.adapter.out.client.dto;

import java.math.BigDecimal;

public record DepositWithdrawRequest(BigDecimal amount) {}
