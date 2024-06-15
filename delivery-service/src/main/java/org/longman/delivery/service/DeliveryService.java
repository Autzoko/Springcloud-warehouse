package org.longman.delivery.service;

import org.longman.entity.DeliveryEntity;

import java.util.List;

public interface DeliveryService {

    void createDelivery(DeliveryEntity delivery);

    void updateDelivery(DeliveryEntity delivery);

    void deleteDelivery(DeliveryEntity delivery);

    DeliveryEntity getDeliveryById(String id);

    List<DeliveryEntity> getDeliveryBySourceId(Long sourceId);

    void updateDeliveryStatus(String deliveryId, boolean status);
}
