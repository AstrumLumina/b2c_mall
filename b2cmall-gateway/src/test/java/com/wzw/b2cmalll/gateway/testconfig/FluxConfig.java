package com.wzw.b2cmalll.gateway.testconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class FluxConfig {
    @Bean(name = "testFlux")
    public Flux<String> getFluxData() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Flux.just("A", "B", "C");
    }
}
