package org.longman.delivery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.longman.entity.DeliveryEntity;

import java.util.List;

public interface DeliveryMapper extends BaseMapper<DeliveryEntity> {

    boolean getDeliveryStatus(String deliveryId);

    List<DeliveryEntity> getDeliveryBySourceId(Long sourceId);

    List<DeliveryEntity> getDeliveryByDestinationId(Long destinationId);

    void updateDeliveryStatus(String deliveryId, boolean status);
}
