package com.example.demo.po;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class NormalUser {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;//主键，设置自增

    @TableField("uid")
    private String uid;//普通用户的用户id

    @TableField("email")
    private String email;//普通用户的电子邮件

    @TableField("createtime")
    private LocalDateTime createtime;//普通用户的账号创建时间

    @TableField("lastlogintime")
    private LocalDateTime lastlogintime;//普通用户的上次登录时间

    @TableField("password")
    private String password;//普通用户的密码

    @TableField(exist = false)
    private String code;


}
