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
@TableName("warehouse_user")
public class UserEntity extends BaseEntity {

    @TableId
    private Long id;

    private String name;

    private String password;

    private boolean category;

}
