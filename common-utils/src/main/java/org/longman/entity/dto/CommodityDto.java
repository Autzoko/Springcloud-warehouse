package org.longman.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommodityDto {

    private Long warehouse_id;

    private Long owner_id;

    private Float price;

    private Long stock;
}
