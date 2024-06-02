package org.longman.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.longman.entity.CommodityEntity;
import org.longman.entity.WarehouseEntity;
import org.longman.exception.DataContentError;
import org.longman.exception.ObjectNotExistException;
import org.longman.utils.WarehouseMetaObjectHandler;
import org.longman.warehouse.mapper.CommodityMapper;
import org.longman.warehouse.mapper.WarehouseMapper;
import org.longman.warehouse.service.CommodityService;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Import(WarehouseMetaObjectHandler.class)
public class CommodityServiceImpl implements CommodityService {

    private final CommodityMapper commodityMapper;

    private final WarehouseMapper warehouseMapper;

    @Override
    public void createCommodity(CommodityEntity commodity) {
        checkCommodity(commodity);
        commodityMapper.insert(commodity);
    }

    @Override
    public void updateCommodity(CommodityEntity commodity) {
        checkCommodity(commodity);
        commodityMapper.updateById(commodity);
    }

    @Override
    public void deleteCommodity(String commodityId) {
        commodityMapper.deleteById(commodityId);
    }

    @Override
    public List<CommodityEntity> getCommodityByOwnerId(Long ownerId) {
        LambdaQueryWrapper<CommodityEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommodityEntity::getOwner_id, ownerId);
        return commodityMapper.selectList(wrapper);
    }

    @Override
    public List<CommodityEntity> getCommodityByWarehouseId(Long warehouseId) {
        LambdaQueryWrapper<CommodityEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommodityEntity::getWarehouse_id, warehouseId);
        return commodityMapper.selectList(wrapper);
    }

    @Override
    public void updateCommodityStock(String commodityId, Long stock) {
        if (stock < 0) {
            throw new DataContentError("stock can not be less than 0");
        }
        commodityMapper.updateCommodityStock(commodityId, stock);
    }

    @Override
    public void updateCommodityPrice(String commodityId, Float price) {
        if (price < 0) {
            throw new DataContentError("price can not be less than 0");
        }
        commodityMapper.updateCommodityPrice(commodityId, price);
    }

    @Override
    public Long getCommodityStock(String commodityId) {
        LambdaQueryWrapper<CommodityEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommodityEntity::getId, commodityId);
        Long itemCount = commodityMapper.selectCount(wrapper);
        if (itemCount == 0) {
            throw new ObjectNotExistException("commodity not exist");
        }
        return commodityMapper.getCommodityStock(commodityId);
    }

    private void checkCommodity(CommodityEntity commodity) {
        // check owner exist

        LambdaQueryWrapper<WarehouseEntity> warehouseWrapper = new LambdaQueryWrapper<>();
        warehouseWrapper.eq(WarehouseEntity::getId, commodity.getWarehouse_id());
        long warehouseCount = warehouseMapper.selectCount(warehouseWrapper);
        if (warehouseCount == 0) {
            throw new ObjectNotExistException("warehouse not exist");
        }

        if (commodity.getPrice() < 0.0) {
            throw new DataContentError("commodity price is negative");
        }

        if (commodity.getStock() < 0) {
            throw new DataContentError("stock is negative");
        }

    }
}
