package com.wzw.b2cmalll.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.Date;
import java.util.List;

@SpringBootTest  //该类需要和 主启动类在同一个包下,否则需要使用其他注解指明主启动类的地址
class B2cmallGatewayApplicationTests {

    @Test
    void contextLoads() {



    }

    /*
    * 经过测试,除了返回flux要在controller层才有效,在其他层仍然是在同一线程中,并没有交由spring webflux去同意管理
    * 导致效果类似是同步,使用上类似于 stream
    * 要使用flux达到响应式异步的效果,需要在controller handler 层放回flux 或mono
    * 在其他层尽量避免使用,使用了没有效果,除非由于他人只留下了flux类的的接口导致不得已而为之
    * */
    public Flux<String> getFluxData() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Flux.just("A", "B", "C");
    }


    @Test
    public void processData() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            System.out.println(new Date().getTime());
            Flux<String> fluxData = getFluxData();
            System.out.println(new Date().getTime());

            Disposable subscribe = fluxData.subscribe(value -> {
                System.out.println("Received: " + value);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            });

            System.out.println("Processing complete");
            System.out.println(new Date().getTime());
        }

    }

    @Autowired
    @Qualifier("testFlux")
    private Flux<String>  flux;

    @Test
    public void processData2() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            System.out.println(new Date().getTime());
            Flux<String> fluxData = flux;
            System.out.println(new Date().getTime());

            Disposable subscribe = fluxData.subscribe(value -> {
                System.out.println("Received: " + value);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            });

            System.out.println("Processing complete");
            System.out.println(new Date().getTime());
        }

    }

    @Test
    public void webclientTest(){


//        //可行的方案一
//        WebClient.builder()
//                .build()
//                .get()
//                .uri("http://localhost:8084/b2cmallProduct/swagger-resources")
//                .retrieve()
//                .bodyToFlux(SwaggerResource.class)
//                .subscribe(swaggerResource -> {
//                    System.out.println(swaggerResource);
//                    System.out.println(swaggerResource.getName()+" : "+swaggerResource.getUrl());
//                });
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//
//        }


//        //可行的方案二
//        WebClient.builder()
//                .build()
//                .get()
//                .uri("http://localhost:8084/b2cmallProduct/swagger-resources")
//                .retrieve()
//                .bodyToFlux(SwaggerResource.class)
//                .collectList()
//                .block()
//                .forEach(swaggerResource -> {
//                    System.out.println(swaggerResource);
//                    System.out.println(swaggerResource.getName()+" : "+swaggerResource.getUrl());
//                });


//        //方案三
//        WebClient.builder()
//                .build()
//                .get()
//                .uri("http://localhost:8084/b2cmallProduct/swagger-resources")
//                .retrieve()
//                .bodyToFlux(SwaggerResource.class)
//                .collectList()
//                .doOnSuccess(swaggerResources -> {
//                    swaggerResources.forEach(
//                            swaggerResource -> {
//                                System.out.println(swaggerResource);
//                                System.out.println(swaggerResource.getName()+" : "+swaggerResource.getUrl());
//                            }
//                    );
//                })
//                .doOnError(throwable -> {
//                    System.out.println(throwable.getMessage());
//                })
//                .subscribe();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//
//        }


    }

}
