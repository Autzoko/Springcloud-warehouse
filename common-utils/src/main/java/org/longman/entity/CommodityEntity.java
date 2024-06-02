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
@TableName("warehouse_commodity")
public class CommodityEntity extends BaseEntity {

    @TableId
    private String id;

    private Long owner_id;

    private Long warehouse_id;

    private Float price;

    private Long stock;

}
