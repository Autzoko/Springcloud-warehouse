package org.longman.microservice.utils;

import lombok.RequiredArgsConstructor;
import org.longman.common.ServiceUrls;
import org.longman.entity.dto.DeliveryDto;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class HandleDelivery {

    private final RestTemplate restTemplate;

    private DeliveryDto getDelivery(String deliveryId) {
        return restTemplate.getForObject(ServiceUrls.DELIVERY_SERVICE_URL + "/fetch-delivery", DeliveryDto.class, deliveryId);
    }

    public Long getSource(String deliveryId) {
        return getDelivery(deliveryId).getSource_id();
    }
}
