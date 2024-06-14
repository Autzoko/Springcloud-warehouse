package com.example.demo.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.example.demo.mapper")
public class MybatisPlusConfig {

    /**
    * @description: 分页插件
    * 内部使用 @Bean 注解将 PaginationInterceptor 交给 Spring 容器管理。
    * @param
    * @return
    * @author dcy
    * @date 2024/4/1
    */
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
    }

}

