package org.longman.microservice.service;

import org.longman.entity.TransactionEntity;
import org.longman.entity.dto.DeliveryDto;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

public interface TransactionService {

    void createTransaction(TransactionEntity transaction);

    void updateTransaction(TransactionEntity transaction);

    void deleteTransaction(String transactionId);

    TransactionEntity getTransaction(String transactionId);

    List<TransactionEntity> getTransactionsByProviderId(Long providerId);

    List<TransactionEntity> getTransactionsByConsumerId(Long consumerId);

    List<TransactionEntity> getTransactionsByCommodityId(String commodityId);

    boolean isPaymentSuccess(Float price);

    boolean isCommodityUpdated(String id, Long stock);

    String getWarehouseId(String commodity_id);

    boolean deliver(DeliveryDto deliveryDto);

    boolean isTransactionExist(String transactionId);

    void updateTransactionStatus(String transactionId, String transactionStatus);

}
