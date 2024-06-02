package org.longman.warehouse.service;

import org.longman.entity.CommodityEntity;

import java.util.List;

public interface CommodityService {

    void createCommodity(CommodityEntity commodity);

    void updateCommodity(CommodityEntity commodity);

    void deleteCommodity(String commodityId);

    List<CommodityEntity> getCommodityByOwnerId(Long ownerId);

    List<CommodityEntity> getCommodityByWarehouseId(Long warehouseId);

    void updateCommodityStock(String commodityId, Long stock);

    void updateCommodityPrice(String commodityId, Float price);

    Long getCommodityStock(String commodityId);
}
