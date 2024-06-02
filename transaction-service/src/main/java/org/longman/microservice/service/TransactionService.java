package org.longman.microservice.service;

import org.longman.entity.TransactionEntity;

import java.util.List;

public interface TransactionService {

    void createTransaction(TransactionEntity transaction);

    void updateTransaction(TransactionEntity transaction);

    void deleteTransaction(String transactionId);

    TransactionEntity getTransaction(String transactionId);

    List<TransactionEntity> getTransactionsByProviderId(Long providerId);

    List<TransactionEntity> getTransactionsByConsumerId(Long consumerId);

    List<TransactionEntity> getTransactionsByCommodityId(String commodityId);

}
