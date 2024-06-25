package org.longman.microservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.longman.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionMapper extends BaseMapper<TransactionEntity> {

    List<TransactionEntity> findByProviderId(Long providerId);

    List<TransactionEntity> findByConsumerId(Long consumerId);

    List<TransactionEntity> findByCommodityId(Long commodityId);

    void updateTransactionStatus(@Param("transactionId") String transactionId, @Param("status") String status);

}
