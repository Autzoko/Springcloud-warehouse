package org.longman.microservice.service;

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
}
