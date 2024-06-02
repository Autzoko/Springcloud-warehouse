package org.longman.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.longman.entity.WarehouseEntity;
import org.longman.exception.DataContentError;
import org.longman.utils.WarehouseMetaObjectHandler;
import org.longman.warehouse.mapper.WarehouseMapper;
import org.longman.warehouse.service.WarehouseService;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Import(WarehouseMetaObjectHandler.class)
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseMapper warehouseMapper;

    @Override
    public void createWarehouse(WarehouseEntity warehouse) {
        checkWarehouse(warehouse);
        warehouseMapper.insert(warehouse);
    }

    @Override
    public void updateWarehouse(WarehouseEntity warehouse) {
        checkWarehouse(warehouse);
        warehouseMapper.updateById(warehouse);
    }

    @Override
    public void updateWarehouseStock(Long warehouseId, Long stock) {
        if (stock < 0) {
            throw new DataContentError("warehouse stock must be greater than 0");
        }

        warehouseMapper.updateWarehouseStock(warehouseId, stock);
    }

    @Override
    public void deleteWarehouse(Long warehouseId) {
        warehouseMapper.deleteById(warehouseId);
    }

    @Override
    public List<WarehouseEntity> getWarehouseByOwnerId(Long ownerId) {
        LambdaQueryWrapper<WarehouseEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseEntity::getOwner_id, ownerId);
        return warehouseMapper.selectList(wrapper);
    }

    @Override
    public Long getWarehouseStock(Long warehouseId) {
        return warehouseMapper.getWarehouseStock(warehouseId);
    }

    private void checkWarehouse(WarehouseEntity warehouse) {
        //check owner user exist here

        if (warehouse.getStock() < 0) {
            throw new DataContentError("warehouse stock cannot less than 0");
        }
    }
}
