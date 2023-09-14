package com.wzw.b2cmall.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wzw.b2cmall.resource.feign")
public class B2cmallResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(B2cmallResourceApplication.class, args);
    }

}
