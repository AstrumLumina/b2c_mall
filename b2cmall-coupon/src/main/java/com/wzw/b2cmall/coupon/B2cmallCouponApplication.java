package com.wzw.b2cmall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class B2cmallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(B2cmallCouponApplication.class, args);
    }

}
