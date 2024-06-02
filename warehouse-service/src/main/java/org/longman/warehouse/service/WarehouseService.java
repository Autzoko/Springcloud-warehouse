package org.longman.warehouse.service;

import org.longman.entity.WarehouseEntity;

import java.util.List;

public interface WarehouseService {

    void createWarehouse(WarehouseEntity warehouse);

    void updateWarehouse(WarehouseEntity warehouse);

    void updateWarehouseStock(Long warehouseId, Long stock);

    void deleteWarehouse(Long warehouseId);

    List<WarehouseEntity> getWarehouseByOwnerId(Long ownerId);

    Long getWarehouseStock(Long warehouseId);
}
