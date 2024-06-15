package org.longman.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.longman.entity.WarehouseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseMapper extends BaseMapper<WarehouseEntity> {

    List<WarehouseEntity> findByOwnerId(Long ownerId);

    Long getWarehouseStock(Long warehouseId);

    void updateWarehouseStock(@Param("warehouseId") Long warehouseId, @Param("stock") Long stock);

}
