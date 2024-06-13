package org.longman.delivery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.longman.delivery.mapper")
public class RunDeliveryService {
    public static void main(String[] args) {
        SpringApplication.run(RunDeliveryService.class, args);
    }
}