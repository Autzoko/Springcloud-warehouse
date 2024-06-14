package org.longman.microservice.service;

import org.longman.entity.dto.DeliveryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient("transaction-service")
public interface TransactionClient {

    @GetMapping("/payment/pay/{price}")
    ResponseEntity<Object> pay(@PathVariable("price") Float price);

    @PutMapping("/commodity/update-stock")
    ResponseEntity<Object> updateStock(@RequestParam(name = "id") String id, @RequestParam(name = "stock") Long stock);

    @PostMapping("/delivery/create")
    ResponseEntity<Object> createDelivery(@RequestBody DeliveryDto deliveryDto);

    @GetMapping("/commodity/get-warehouse/{id}")
    ResponseEntity<Object> getWarehouseIdByCommodityId(@PathVariable(name = "id") String id);
}
