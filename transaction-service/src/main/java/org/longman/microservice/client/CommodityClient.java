package org.longman.microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "warehouse-warehouse-service")
public interface CommodityClient {
    @PutMapping("/commodity/update-stock")
    ResponseEntity<Object> updateStock(@RequestParam(name = "id") String id, @RequestParam(name = "stock") Long stock);

    @GetMapping("/commodity/get-warehouse/{id}")
    ResponseEntity<Object> getWarehouseIdByCommodityId(@PathVariable(name = "id") String id);
}
