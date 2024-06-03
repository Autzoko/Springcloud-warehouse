package org.longman.delivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.longman.delivery.mapper.DeliveryMapper;
import org.longman.delivery.service.DeliveryService;
import org.longman.entity.DeliveryEntity;
import org.longman.exception.IdConflictException;
import org.longman.exception.MissingFieldException;
import org.longman.utils.WarehouseMetaObjectHandler;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Import(WarehouseMetaObjectHandler.class)
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryMapper deliveryMapper;

    @Override
    public void createDelivery(DeliveryEntity delivery) {
        checkDelivery(delivery);
        deliveryMapper.insert(delivery);
    }

    @Override
    public void updateDelivery(DeliveryEntity delivery) {
        if (Objects.isNull(delivery.getId())) {
            throw new MissingFieldException("id");
        }
        if (Objects.isNull(delivery.getSource_id())) {
            throw new MissingFieldException("source_id");
        }
        if (Objects.isNull(delivery.getDestination_id())) {
            throw new MissingFieldException("destination_id");
        }

        deliveryMapper.updateById(delivery);
    }

    @Override
    public void deleteDelivery(DeliveryEntity delivery) {
        LambdaQueryWrapper<DeliveryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeliveryEntity::getId, delivery.getId());
        deliveryMapper.delete(wrapper);
    }

    @Override
    public DeliveryEntity getDeliveryById(String id) {
        LambdaQueryWrapper<DeliveryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeliveryEntity::getId, id);
        return deliveryMapper.selectOne(wrapper);
    }

    @Override
    public List<DeliveryEntity> getDeliveryBySourceId(Long sourceId) {
        LambdaQueryWrapper<DeliveryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeliveryEntity::getSource_id, sourceId);
        return deliveryMapper.selectList(wrapper);
    }

    @Override
    public List<DeliveryEntity> getDeliveryByDestinationId(Long destinationId) {
        LambdaQueryWrapper<DeliveryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeliveryEntity::getDestination_id, destinationId);
        return deliveryMapper.selectList(wrapper);
    }

    @Override
    public void updateDeliveryStatus(String deliveryId, boolean status) {
        deliveryMapper.updateDeliveryStatus(deliveryId, status);
    }

    private void checkDelivery(DeliveryEntity delivery) {
        LambdaQueryWrapper<DeliveryEntity> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(DeliveryEntity::getId, delivery.getId());
        long itemCount = deliveryMapper.selectCount(wrapper);

        if (itemCount > 0) {
            throw new IdConflictException("delivery id exists");
        }

        if (Objects.isNull(delivery.getSource_id())) {
            throw new MissingFieldException("source_id");
        }

        if (Objects.isNull(delivery.getDestination_id())) {
            throw new MissingFieldException("destination_id");
        }
    }
}
