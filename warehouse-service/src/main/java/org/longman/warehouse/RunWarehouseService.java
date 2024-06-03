package org.longman.warehouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.longman.warehouse.mapper")
public class RunWarehouseService {
    public static void main(String[] args) {
        SpringApplication.run(RunWarehouseService.class, args);
    }
}