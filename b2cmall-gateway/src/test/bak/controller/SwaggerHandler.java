package com.wzw.b2cmalll.gateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/*
在Spring Cloud Gateway中，不能直接定义自己的处理请求的controller。
Spring Cloud Gateway是基于Netty的非阻塞编程模型，根据预定义的路由规则，
将请求转发到相应的目标服务。它并不直接处理请求，而是通过代理转发请求。
如果您需要自定义处理请求的逻辑，可以考虑使用Spring Boot中的普通Web MVC或WebFlux框架来创建自己的controller。
对于更复杂的定制需求，您还可以考虑使用Spring Cloud Gateway的过滤器和拦截器来对请求进行处理。
这些过滤器和拦截器提供了在请求到达目标服务之前或返回响应之后进行操作的机制。
总结起来，Spring Cloud Gateway的设计目的是提供高性能、高可扩展性和灵活的API网关服务，
它并不直接支持定义自己的处理请求的controller。如果您需要更精细的请求处理逻辑，可以选择使用其他适合的框架。
* */

/**
 * swagger的数据接口
 * 在访问swagger-ui中会拉去此接口的数据 这个类主要用于提供swagger各种资源
 * @author whx
 * @date 2022/4/22
 */
//@RestController
//@RequestMapping("/swagger-resources")
public class SwaggerHandler {

    @Autowired(required = false)
    private  SecurityConfiguration securityConfiguration;
    @Autowired(required = false)
    private  UiConfiguration uiConfiguration;
    @Autowired
    private  SwaggerResourcesProvider swaggerResourcesProvider;



    /*
        /swagger-resources/configuration/security 接口用于获取 Swagger 的安全配置信息。
        /swagger-resources/configuration/ui 接口用于获取 Swagger 的界面配置信息。
        这两个接口在生成 Swagger 文档时会被调用。
        返回的数据是配置信息，用于定制 Swagger 的安全和界面设置，从而生成符合需求的 Swagger 文档。
        这两个扩展接口有ui特定的ui框架实现,调用时机也不固定,需要更具ui框架来实现,
        我猜测这个因该要在初次获取文档之前,这样才能进行安全控制,但是在gateway模式下,每个服务的安全配置信息都不同,如果只获取一次安全信息就可能出现问题,
        这里要根据ui框架的实现方式检查测试以防出现错误
   * */

    /**
     * @Description: 该接口是自定义的扩展接口,需要结合特定的swagger-ui框架返回特定的安全信息,实现安全控制,这里不进行安全控制,直接放回空即可
     * @Param
     * @return reactor.core.publisher.Mono<org.springframework.http.ResponseEntity < springfox.documentation.swagger.web.SecurityConfiguration>>
     * date 2023/8/14 13:43
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    @GetMapping("/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration(){
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    /**
     * @Description: 该接口是自定义的扩展接口, 需要结合特定的swagger-ui框架返回特定的界面信息,实现界面自定义,这里不进行安全控制,直接放回空即可
     * @Param
     * @return reactor.core.publisher.Mono<org.springframework.http.ResponseEntity < springfox.documentation.swagger.web.UiConfiguration>>
     * date 2023/8/14 13:46
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    @GetMapping("configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration(){
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()),HttpStatus.OK));
    }

    /**
     * @Description: 获取swagger restful api文档信息
     * @Param
     * @return reactor.core.publisher.Mono<org.springframework.http.ResponseEntity < java.util.List < springfox.documentation.swagger.web.SwaggerResource>>>
     * date 2023/8/14 13:43
     * @author WangZhiWen
     * @Version: 1.0
     * @since JDK 11
     * @Company: 版权所有
     **/
    @GetMapping
    public Mono<ResponseEntity<List<SwaggerResource>>> swaggerResources(){
        return Mono.fromSupplier(()->{
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName("test");
            swaggerResource.setLocation("test");
            ArrayList<SwaggerResource> swaggerResources = new ArrayList<>();
            swaggerResources.add(swaggerResource);
            return new ResponseEntity<List<SwaggerResource>>(swaggerResources,HttpStatus.OK);
        });
    }
}
