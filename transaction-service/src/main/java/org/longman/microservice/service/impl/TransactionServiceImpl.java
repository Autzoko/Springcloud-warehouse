package org.longman.microservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.transaction.Transaction;
import org.longman.exception.IdConflictException;
import org.longman.exception.MissingFieldException;
import org.longman.microservice.entity.TransactionEntity;
import org.longman.microservice.mapper.TransactionMapper;
import org.longman.microservice.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;

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
