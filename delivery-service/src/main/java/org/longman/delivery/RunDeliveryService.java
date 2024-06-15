package org.longman.delivery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("org.longman.delivery.mapper")
public class RunDeliveryService {
    @LoadBalanced
    public static void main(String[] args) {
        SpringApplication.run(RunDeliveryService.class, args);
    }
}