package com.wzw.b2cmalll.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;

/*
用@SpringBootApplication(exclude = RibbonRule.class)排除类@bean注入的类的时候报错
The following classes could not be excluded because they are not auto-configuration classes.....
在 stackoverflow  和 spring 的issue 找到了答案,@SpringBootApplication的exclude
专门用来排除auto-configuration 也就是我们说的自动配置的类的,
如果我们想排除自己定义的@Bean,可以用
@ComponentScan(excludeFilters= {@ComponentScan.Filter(type=FilterType.ANNOTATION, value= {AvoidScan.class})})
*/

//@SpringBootApplication(exclude = {InMemorySwaggerResourcesProvider.class})

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value =springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider.class )})
public class B2cmallGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(B2cmallGatewayApplication.class, args);
    }

}
