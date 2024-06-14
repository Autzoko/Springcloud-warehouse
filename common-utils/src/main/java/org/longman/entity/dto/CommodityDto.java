package org.longman.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommodityDto {

    private String id;

    private String name;

    private Long warehouse_id;

    private Float price;

    private Long stock;
}
