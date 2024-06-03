package org.longman.warehouse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.longman.entity.CommodityEntity;
import org.longman.entity.dto.CommodityDto;
import org.longman.exception.JsonDataError;
import org.longman.exception.ObjectNotExistException;
import org.longman.utils.BaseController;
import org.longman.warehouse.service.CommodityService;
import org.longman.warehouse.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/commodity")
public class CommodityController extends BaseController {

    private final CommodityService commodityService;

    private final WarehouseService warehouseService;

    @PostMapping("/add")
    public ResponseEntity<Object> addCommodity(@RequestBody CommodityDto commodityDto) {
        try {
            validateCommodityInfo(commodityDto);

            CommodityEntity commodity = new CommodityEntity();

            commodity.setId(UUID.randomUUID().toString());
            commodity.setOwner_id(commodityDto.getOwner_id());
            commodity.setWarehouse_id(commodityDto.getWarehouse_id());
            commodity.setPrice(commodityDto.getPrice());
            commodity.setStock(commodityDto.getStock());

            commodityService.createCommodity(commodity);

            return success("commodity added");
        } catch (JsonDataError e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("json data error");
        } catch (ObjectNotExistException e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCommodity(@RequestParam(name = "id") String id) {
        try {
            commodityService.deleteCommodity(id);
            return success("commodity deleted");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("commodity delete error");
        }
    }

    @PutMapping("/update-stock/{id}")
    public ResponseEntity<Object> updateStock(@PathVariable(name = "id") String id, @RequestParam(name = "stock") Long stock) {
        try {
            if (Objects.isNull(stock)) {
                throw new JsonDataError("stock is null");
            }

            CommodityEntity currentCommodity = commodityService.getCommodityById(id);
            Long variance = currentCommodity.getStock() - stock;
            Long oldWarehouseStock = warehouseService.getWarehouseStock(currentCommodity.getWarehouse_id());

            warehouseService.updateWarehouseStock(currentCommodity.getWarehouse_id(), oldWarehouseStock + variance);

            commodityService.updateCommodityStock(id, stock);
            return success("stock updated");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("stock update error");
        }
    }

    @PutMapping("/update-price/{id}")
    public ResponseEntity<Object> updatePrice(@PathVariable(name = "id") String id, @RequestParam(name = "price") Float price) {
        try {
            commodityService.updateCommodityPrice(id, price);
            return success("price updated");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("price update error");
        }
    }

    @GetMapping("/fetch-user-commodity")
    public ResponseEntity<Object> getCommodityByOwnerId(@RequestParam(name = "ownerId") Long ownerId) {
        try {
            List<CommodityEntity> commodities = commodityService.getCommodityByOwnerId(ownerId);
            List<CommodityDto> commodityDtos = getCommodityDtoList(commodities);
            return success(commodityDtos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("fetch user's commodity error");
        }
    }

    @GetMapping("/fetch-warehouse-commodity")
    public ResponseEntity<Object> getCommodityByWarehouseId(@RequestParam(name = "warehouseId") Long warehouseId) {
        try {
            List<CommodityEntity> commodities = commodityService.getCommodityByWarehouseId(warehouseId);
            List<CommodityDto> commodityDtos = getCommodityDtoList(commodities);
            return success(commodityDtos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("fetch warehouse commodity error");
        }
    }

    private static List<CommodityDto> getCommodityDtoList(List<CommodityEntity> commodities) {
        List<CommodityDto> commodityDtoList = new ArrayList<>();
        for (CommodityEntity commodity : commodities) {
            CommodityDto commodityDto = new CommodityDto();
            commodityDto.setId(commodity.getId());
            commodityDto.setOwner_id(commodity.getOwner_id());
            commodityDto.setWarehouse_id(commodity.getWarehouse_id());
            commodityDto.setPrice(commodity.getPrice());
            commodityDto.setStock(commodity.getStock());
            commodityDtoList.add(commodityDto);
        }
        return commodityDtoList;
    }

    private void validateCommodityInfo(CommodityDto commodityDto) {
        if (commodityDto == null) {
            throw new JsonDataError("commodity data not found");
        }
        if(Objects.isNull(commodityDto.getOwner_id())) {
            throw new JsonDataError("owner id not found");
        }
        if(Objects.isNull(commodityDto.getPrice())) {
            throw new JsonDataError("price not found");
        }
        if (Objects.isNull(commodityDto.getStock())) {
            throw new JsonDataError("stock not found");
        }
        if (Objects.isNull(commodityDto.getWarehouse_id())) {
            throw new JsonDataError("warehouse id not found");
        }
    }
}
