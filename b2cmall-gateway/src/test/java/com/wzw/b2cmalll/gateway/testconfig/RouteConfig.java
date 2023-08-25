package com.wzw.b2cmalll.gateway.testconfig;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator b2cmallMember(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route("example",
                        r -> r.path("/example/**")
                                .uri("http://example.com"))
                .route("api",
                        r -> r.path("/api/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://api-service"))
                .build();
    }
}
