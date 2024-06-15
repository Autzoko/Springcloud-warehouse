package org.longman.microservice.client;

import org.longman.entity.dto.DeliveryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "delivery-service")
public interface DeliveryClient {
    @PostMapping("/delivery/create")
    ResponseEntity<Object> createDelivery(@RequestBody DeliveryDto deliveryDto);
}
