package org.longman.microservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("org.longman.microservice.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@EnableHystrix
public class RunTransactionService {
    @LoadBalanced
    public static void main(String[] args) {
        SpringApplication.run(RunTransactionService.class, args);
    }
}