package org.longman.microservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.longman.microservice.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionMapper extends BaseMapper<TransactionEntity> {

    List<TransactionEntity> findByProviderId(Long providerId);

    List<TransactionEntity> findByConsumerId(Long consumerId);

    List<TransactionEntity> findByCommodityId(Long commodityId);

}
