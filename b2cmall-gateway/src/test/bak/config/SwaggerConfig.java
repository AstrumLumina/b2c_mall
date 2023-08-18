package com.wzw.b2cmalll.gateway.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;


/**
 * @Description: swagger配置类 该类主要用于提供两个bean: securityConfiguration和uiConfiguration。这两个bean在后续会被调用
 * @Param null null
 * @return
 * date 2023/8/14 13:19
 * @author WangZhiWen
 * @Version: 1.0
 * @since JDK 11
 * @Company: 版权所有
 **/
@Configuration
public class SwaggerConfig {

    //这两个bean暂时用不到
    //@Bean
    public SecurityConfiguration securityConfiguration(){
        return SecurityConfigurationBuilder.builder().build();
    }

    //@Bean
    public UiConfiguration uiConfiguration(){
        return UiConfigurationBuilder.builder().build();
    }





}
