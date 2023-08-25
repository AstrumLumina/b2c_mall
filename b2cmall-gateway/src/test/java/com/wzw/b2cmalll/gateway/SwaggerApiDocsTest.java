package com.wzw.b2cmalll.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
public class SwaggerApiDocsTest {


    @Autowired
    @Qualifier(value = "commonWebClient")
    private WebClient commonWebclient;

    //@Test
    public void testSaggerApidocsServers(){
        String swaggerApidosFrom8084 = commonWebclient
                .get()
                .uri("http://localhost:8084/b2cmall/product//v3/api-docs?group=testGroup")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(swaggerApidosFrom8084);
        System.out.println("===================");

        //http://localhost:8086/b2cmall/product//v3/api-docs?group=testGroup
        String swaggerApidosFrom8086 = commonWebclient
                .get()
                .uri("http://localhost:8086/b2cmall/product//v3/api-docs?group=testGroup")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(swaggerApidosFrom8086);

    }
}
