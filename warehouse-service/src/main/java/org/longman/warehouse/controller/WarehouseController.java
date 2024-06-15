package org.longman.warehouse.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.longman.entity.WarehouseEntity;
import org.longman.entity.dto.WarehouseDto;
import org.longman.exception.JsonDataError;
import org.longman.utils.BaseController;
import org.longman.warehouse.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController extends BaseController {

    private final WarehouseService warehouseService;

    @PostMapping("/new")
    public ResponseEntity<Object> createWarehouse(@RequestBody WarehouseDto warehouseDto) {
        try {
            validateWarehouseInfo(warehouseDto);

            WarehouseEntity warehouse = new WarehouseEntity();
            warehouse.setStock(warehouseDto.getStock());

            warehouseService.createWarehouse(warehouse);
            return success("warehouse created");
        } catch (JsonDataError e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("json data error");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteWarehouse(@RequestParam(name = "id") Long id) {
        try {
            warehouseService.deleteWarehouse(id);
            return success("warehouse deleted");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("warehouse delete error");
        }
    }

    private static List<WarehouseDto> getWarehouseDtoList(List<WarehouseEntity> warehouses) {
        List<WarehouseDto> warehouseDtoList = new ArrayList<>();
        for (WarehouseEntity warehouse : warehouses) {
            WarehouseDto warehouseDto = new WarehouseDto();
            warehouseDto.setId(warehouse.getId());
            warehouseDto.setStock(warehouse.getStock());
            warehouseDtoList.add(warehouseDto);
        }
        return warehouseDtoList;
    }

    private static void validateWarehouseInfo(WarehouseDto warehouseDto) {
        if (warehouseDto == null) {
            throw new JsonDataError("warehouse data not found");
        }
        if (Objects.isNull(warehouseDto.getStock()) || warehouseDto.getStock() < 0) {
            throw new JsonDataError("stock not found");
        }
    }
}
