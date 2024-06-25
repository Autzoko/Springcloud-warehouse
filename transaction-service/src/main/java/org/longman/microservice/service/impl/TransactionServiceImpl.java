package org.longman.microservice.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.longman.entity.dto.DeliveryDto;
import org.longman.exception.DataContentError;
import org.longman.exception.IdConflictException;
import org.longman.exception.MissingFieldException;
import org.longman.entity.TransactionEntity;
import org.longman.exception.ObjectNotExistException;
import org.longman.microservice.client.CommodityClient;
import org.longman.microservice.client.DeliveryClient;
import org.longman.microservice.client.PaymentClient;
import org.longman.microservice.mapper.TransactionMapper;
import org.longman.microservice.service.TransactionService;
import org.longman.utils.WarehouseMetaObjectHandler;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Import(WarehouseMetaObjectHandler.class)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;

    private final DeliveryClient deliveryClient;
    private final CommodityClient commodityClient;
    private final PaymentClient paymentClient;

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
        ResponseEntity<Object> response = paymentClient.pay(price);
        return isResponseSucceeded(response);
    }

    @Override
    public boolean isCommodityUpdated(String id, Long stock) {
        ResponseEntity<Object> response = commodityClient.updateStock(id, stock);
        return isResponseSucceeded(response);
    }

    @Override
    public String getWarehouseId(String commodity_id) {
        ResponseEntity<Object> response = commodityClient.getWarehouseIdByCommodityId(commodity_id);
        if (response.getBody() instanceof Map<?,?>) {
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            System.out.println(responseBody);
            return responseBody.get("data").toString();
        }
        return "null";
    }

    @Override
    public boolean deliver(DeliveryDto deliveryDto) {
        ResponseEntity<Object> response = deliveryClient.createDelivery(deliveryDto);
        return isResponseSucceeded(response);
    }

    @Override
    public boolean isTransactionExist(String transactionId) {
        LambdaQueryWrapper<TransactionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransactionEntity::getId, transactionId);
        return transactionMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public void updateTransactionStatus(String transactionId, String transactionStatus) {
        if (!isTransactionExist(transactionId)) {
            throw new ObjectNotExistException("transaction not exist");
        }
        if (!Objects.equals(transactionStatus, "pending") || !Objects.equals(transactionStatus, "ongoing")
        || !Objects.equals(transactionStatus, "complete") || !Objects.equals(transactionStatus, "cancelled")) {
            throw new DataContentError("status content error: should be \"pending\", \"ongoing\", \"complete\" or \"cancelled\"");
        }
        updateTransactionStatus(transactionId, transactionStatus);
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

    private boolean isResponseSucceeded(ResponseEntity<Object> response) {
        if (response.getBody() instanceof Map<?,?>) {
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            System.out.println((Boolean) responseBody.get("success"));
            return (Boolean) responseBody.get("success");
        }
        return false;
    }
}
