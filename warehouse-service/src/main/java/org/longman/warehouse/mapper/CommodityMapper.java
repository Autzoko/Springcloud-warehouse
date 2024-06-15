package org.longman.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.longman.entity.CommodityEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityMapper extends BaseMapper<CommodityEntity> {

    List<CommodityEntity> findByOwnerId(Long ownerId);

    List<CommodityEntity> findByWarehouseId(Long warehouseId);

    Long getCommodityStock(String commodityId);

    void updateCommodityStock(@Param("commodityId") String commodityId, @Param("stock") Long stock);

    void updateCommodityPrice(String commodityId, Float price);

    CommodityEntity findById(String id);
}
