package org.longman.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

    private String id;

    private Long provider_id;

    private Long consumer_id;

    private String commodity_id;

    private Long num_transaction;

    private Float price;

    private String status;

}
