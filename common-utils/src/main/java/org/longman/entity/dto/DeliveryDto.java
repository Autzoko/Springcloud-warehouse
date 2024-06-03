package org.longman.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDto {

    private String id;

    private Long source_id;

    private Long destination_id;

    private boolean status;

}
