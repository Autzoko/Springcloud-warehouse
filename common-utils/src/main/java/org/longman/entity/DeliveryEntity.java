package org.longman.entity;

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
@TableName("warehouse_delivery")
public class DeliveryEntity extends BaseEntity {

    @TableId
    private String id;

    private Long source_id;

    private Long destination_id;

    private boolean status;

}
