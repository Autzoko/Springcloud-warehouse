package org.longman.microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "payment-service")
public interface PaymentClient {
    @GetMapping("/payment/pay/{price}")
    ResponseEntity<Object> pay(@PathVariable("price") Float price);
}
