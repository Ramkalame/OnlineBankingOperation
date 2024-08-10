package com.onlineBankingOperations.service;

import java.math.BigDecimal;

public interface AccountService {

    void updateClientBalance();
    String transferMoney(Long senderClientId, Long receiverClientId, Double money);
}
