package org.longman.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.longman.entity.CommodityEntity;

import java.util.List;

public interface CommodityMapper extends BaseMapper<CommodityEntity> {

    List<CommodityEntity> findByOwnerId(Long ownerId);

    List<CommodityEntity> findByWarehouseId(Long warehouseId);

    Long getCommodityStock(String commodityId);

    void updateCommodityStock(String commodityId, Long stock);

    void updateCommodityPrice(String commodityId, Float price);
}
