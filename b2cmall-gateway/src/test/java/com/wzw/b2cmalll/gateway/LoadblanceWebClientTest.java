package com.wzw.b2cmalll.gateway;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.swagger.web.SwaggerResource;

@SpringBootTest
public class LoadblanceWebClientTest {

    @Autowired
    private WebClient loadbalancedWebclient;

    @Autowired
    private WebClient.Builder loadbalancedWebclientBuilder;

    @Test
    public void loadbalancedWebclientTest(){
        loadbalancedWebclient
                .get()
                .uri("/b2cmallProduct/swagger-resources")
                .retrieve()
                .bodyToFlux(SwaggerResource.class)
                .collectList()
                .block()
                .forEach(
                        swaggerResource -> {
                            System.out.println(swaggerResource);
                            System.out.println(swaggerResource.getName()+" : "+swaggerResource.getUrl());
                        }
                );
        System.out.println("=========================");
    }

    @Test
    public void loadbalancedWebclient2Test(){
        loadbalancedWebclientBuilder
                .baseUrl("b2cmall-product")
                .build()
                .get()
                .uri("lb://b2cmall-product/b2cmallProduct/swagger-resources")
                .retrieve()
                .bodyToFlux(SwaggerResource.class)
                .collectList()
                .block()
                .forEach(
                        swaggerResource -> {
                            System.out.println(swaggerResource);
                            System.out.println(swaggerResource.getName()+" : "+swaggerResource.getUrl());
                        }
                );
        System.out.println("=========================");

        loadbalancedWebclientBuilder
                .baseUrl("lb://b2cmall-product")
                .build()
                .get()
                .uri("b2cmallProduct/swagger-resources")
                .retrieve()
                .bodyToFlux(SwaggerResource.class)
                .collectList()
                .block()
                .forEach(
                        swaggerResource -> {
                            System.out.println(swaggerResource);
                            System.out.println(swaggerResource.getName()+" : "+swaggerResource.getUrl());
                        }
                );
        System.out.println("=========================");

        loadbalancedWebclientBuilder
                .baseUrl("lb://b2cmall-product")
                .build()
                .get()
                .uri("/b2cmallProduct/swagger-resources")
                .retrieve()
                .bodyToFlux(SwaggerResource.class)
                .collectList()
                .block()
                .forEach(
                        swaggerResource -> {
                            System.out.println(swaggerResource);
                            System.out.println(swaggerResource.getName()+" : "+swaggerResource.getUrl());
                        }
                );
        System.out.println("=========================");



    }

    @Test
    public void gatewaySwaggerTest(){
        WebClient.builder()
                .build()
                .get()
                .uri("http://localhost:8086/b2cmall/product/swagger-resources")
                .retrieve()
                .bodyToFlux(SwaggerResource.class)
                .subscribe(
                        swaggerResource -> {
                            System.out.println(swaggerResource);
                            System.out.println(swaggerResource.getName()+" : "+swaggerResource.getUrl());
                });
        System.out.println("=========================");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===================");
    }
}
