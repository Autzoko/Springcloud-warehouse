package org.longman.microservice.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.longman.utils.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("warehouse_transaction")
public class TransactionEntity extends BaseEntity {

    @TableId
    private String id;

    private Long provider_id;

    private Long consumer_id;

    private String commodity_id;

    private Long num_transaction;

}
