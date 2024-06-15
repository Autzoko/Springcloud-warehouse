package org.longman.warehouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("org.longman.warehouse.mapper")
public class RunWarehouseService {
    @LoadBalanced
    public static void main(String[] args) {
        SpringApplication.run(RunWarehouseService.class, args);
    }
}