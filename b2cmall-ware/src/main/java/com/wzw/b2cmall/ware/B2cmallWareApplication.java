package com.wzw.b2cmall.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class B2cmallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(B2cmallWareApplication.class, args);
    }

}
