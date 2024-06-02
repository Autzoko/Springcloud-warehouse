package org.longman.microservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.longman.microservice.mapper")
public class RunTransactionService {
    public static void main(String[] args) {
        SpringApplication.run(RunTransactionService.class, args);
    }
}