package org.longman.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.longman.entity.WarehouseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseMapper extends BaseMapper<WarehouseEntity> {

    List<WarehouseEntity> findByOwnerId(Long ownerId);

    Long getWarehouseStock(Long warehouseId);

}
