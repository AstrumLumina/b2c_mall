package com.wzw.b2cmalll.gateway.testconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {
    @Autowired
    private LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction;

    @Bean(value = "loadbalancedWebclient")    //设置了该该过滤器的webclient使用ip和端口访问自己报错,服务不可得 需要使用普通的webclient
    WebClient loadbalancedWebclient(){
        return WebClient.builder()
                .filter(loadBalancedExchangeFilterFunction)
                .build();
    }

    @Bean("loadbalancedWebclientBuilder")
    @LoadBalanced   //注解加再这里才能自定服务
    WebClient.Builder loadbalancedWebclientBuilder(){
        return WebClient.builder();
    }

    @Bean(value = "loadbalancedWebclient2")
    @LoadBalanced //注解加在这里无效,需要使用配置过滤器的方法
    WebClient loadbalancedWebclient2(){
        return WebClient.builder()
                .baseUrl("lb://b2cmall-product")
                .build();
    }

}
