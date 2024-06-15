package org.longman.microservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.longman.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMapper extends BaseMapper<CustomerEntity> {
}
