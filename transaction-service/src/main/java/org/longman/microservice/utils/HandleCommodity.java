package org.longman.microservice.utils;

import org.longman.common.ServiceUrls;
import org.longman.entity.dto.CommodityDto;
import org.springframework.web.client.RestTemplate;

public class HandleCommodity {

    private  RestTemplate restTemplate;

    private CommodityDto getCommodity(String commodityId) {
        return restTemplate.getForObject(ServiceUrls.COMMODITY_SERVICE_URL + "/fetch-commodity", CommodityDto.class, commodityId);
    }

    public Long getWarehouseId(String commodityId) {
        return getCommodity(commodityId).getWarehouse_id();
    }
}
