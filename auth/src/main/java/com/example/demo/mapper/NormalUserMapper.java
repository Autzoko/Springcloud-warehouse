package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.po.NormalUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@Mapper
public interface NormalUserMapper extends BaseMapper<NormalUser> {
    void addUser(NormalUser normalUser);

    @Select("SELECT COUNT(*) > 0 FROM user WHERE uid = #{uid}")
    boolean existsById(String nUid);

    @Select("SELECT * FROM user WHERE email = #{email}")
    NormalUser getByEmail(String email);

    void updateLastLoginTime(@Param("email") String email, @Param("lastLoginTime") LocalDateTime lastLoginTime);
}
