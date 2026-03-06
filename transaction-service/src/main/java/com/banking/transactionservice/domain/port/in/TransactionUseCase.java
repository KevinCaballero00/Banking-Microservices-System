package com.banking.transactionservice.domain.port.in;

import com.banking.transactionservice.infrastructure.adapter.in.web.dto.TransactionResponse;
import com.banking.transactionservice.infrastructure.adapter.in.web.dto.TransferRequest;
import java.util.List;

public interface TransactionUseCase {
    TransactionResponse transfer(TransferRequest request);
    TransactionResponse getTransactionByReference(String reference);
    List<TransactionResponse> getTransactionsByAccount(String accountNumber);
}
