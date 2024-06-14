package org.longman.microservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.longman.entity.dto.DeliveryDto;
import org.longman.exception.IdConflictException;
import org.longman.exception.MissingFieldException;
import org.longman.entity.TransactionEntity;
import org.longman.microservice.mapper.TransactionMapper;
import org.longman.microservice.service.TransactionClient;
import org.longman.microservice.service.TransactionService;
import org.longman.utils.WarehouseMetaObjectHandler;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Import(WarehouseMetaObjectHandler.class)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;

    private final TransactionClient transactionClient;

    @Override
    public void createTransaction(TransactionEntity transaction) {
        checkTransaction(transaction);
        transactionMapper.insert(transaction);
    }

    @Override
    public void updateTransaction(TransactionEntity transaction) {
        checkTransaction(transaction);
        transactionMapper.updateById(transaction);
    }

    @Override
    public void deleteTransaction(String transactionId) {
        LambdaQueryWrapper<TransactionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TransactionEntity::getId, transactionId);
        transactionMapper.delete(wrapper);
    }

    @Override
    public TransactionEntity getTransaction(String transactionId) {
        LambdaQueryWrapper<TransactionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TransactionEntity::getId, transactionId);
        return transactionMapper.selectOne(wrapper);
    }

    @Override
    public List<TransactionEntity> getTransactionsByProviderId(Long providerId) {
        LambdaQueryWrapper<TransactionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TransactionEntity::getProvider_id, providerId);
        return transactionMapper.selectList(wrapper);
    }

    @Override
    public List<TransactionEntity> getTransactionsByConsumerId(Long consumerId) {
        LambdaQueryWrapper<TransactionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TransactionEntity::getConsumer_id, consumerId);
        return transactionMapper.selectList(wrapper);
    }

    @Override
    public List<TransactionEntity> getTransactionsByCommodityId(String commodityId) {
        LambdaQueryWrapper<TransactionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TransactionEntity::getCommodity_id, commodityId);
        return transactionMapper.selectList(wrapper);
    }

    @Override
    public boolean isPaymentSuccess(Float price) {
        ResponseEntity<Object> response = transactionClient.pay(price);
        JSONObject body = JSONObject.parseObject(Objects.requireNonNull(response.getBody()).toString());
        return body.getObject("success", Boolean.class);
    }

    @Override
    public boolean isCommodityUpdated(String id, Long stock) {
        ResponseEntity<Object> response = transactionClient.updateStock(id, stock);
        JSONObject body = JSONObject.parseObject(Objects.requireNonNull(response.getBody()).toString());
        return body.getObject("success", Boolean.class);
    }

    @Override
    public String getWarehouseId(String commodity_id) {
        ResponseEntity<Object> response = transactionClient.getWarehouseIdByCommodityId(commodity_id);
        JSONObject body = JSONObject.parseObject(Objects.requireNonNull(response.getBody()).toString());
        return body.getObject("data", String.class);
    }

    @Override
    public boolean deliver(DeliveryDto deliveryDto) {
        ResponseEntity<Object> response = transactionClient.createDelivery(deliveryDto);
        JSONObject body = JSONObject.parseObject(Objects.requireNonNull(response.getBody()).toString());
        return body.getObject("success", Boolean.class);
    }

    private void checkTransaction(TransactionEntity transaction) {
        LambdaQueryWrapper<TransactionEntity> queryWrapper = new LambdaQueryWrapper<TransactionEntity>();

        queryWrapper.eq(TransactionEntity::getId, transaction.getId());
        long itemCount = transactionMapper.selectCount(queryWrapper);

        if (itemCount != 0) {
            throw new IdConflictException("transaction id exists");
        }

        if (transaction.getProvider_id() == null) {
            throw new MissingFieldException("provider_id");
        }
        if (transaction.getConsumer_id() == null) {
            throw new MissingFieldException("consumer_id");
        }
        if (transaction.getCommodity_id() == null || transaction.getCommodity_id().isEmpty()) {
            throw new MissingFieldException("commodity_id");
        }
    }
}
