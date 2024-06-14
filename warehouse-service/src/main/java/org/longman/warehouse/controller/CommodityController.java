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
            commodity.setName(commodityDto.getName());
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

    @PutMapping("/update-stock")
    public ResponseEntity<Object> updateStock(@RequestParam(name = "id") String id, @RequestParam(name = "stock") Long stock) {
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

    @GetMapping("/fetch-commodity")
    public ResponseEntity<Object> getCommodityById(@RequestParam(name = "id") String id) {
        try {
            CommodityEntity commodity = commodityService.getCommodityById(id);

            CommodityDto commodityDto = new CommodityDto();

            commodityDto.setId(commodity.getId());
            commodityDto.setName(commodity.getName());
            commodityDto.setWarehouse_id(commodity.getWarehouse_id());
            commodityDto.setPrice(commodity.getPrice());
            commodityDto.setStock(commodity.getStock());

            return success(commodityDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("fetch commodity error");
        }
    }

    @GetMapping("/get-warehouse/{id}")
    public ResponseEntity<Object> getWarehouseById(@PathVariable(name = "id") String id) {
        try {
            CommodityEntity commodity = commodityService.getCommodityById(id);
            return success(commodity.getWarehouse_id());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("get warehouse error");
        }
    }

    private static List<CommodityDto> getCommodityDtoList(List<CommodityEntity> commodities) {
        List<CommodityDto> commodityDtoList = new ArrayList<>();
        for (CommodityEntity commodity : commodities) {
            CommodityDto commodityDto = new CommodityDto();
            commodityDto.setId(commodity.getId());
            commodityDto.setName(commodity.getName());
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
        if(Objects.isNull(commodityDto.getName())) {
            throw new JsonDataError("name not found");
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
