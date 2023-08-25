package com.wzw.b2cmall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class B2cmallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(B2cmallMemberApplication.class, args);
    }

}
