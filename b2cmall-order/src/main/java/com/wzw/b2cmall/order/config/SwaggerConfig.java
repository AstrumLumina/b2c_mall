package com.wzw.b2cmall.order.config;


import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name: b2cmall-oms}")
    private String applicationName;
    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.OAS_30)
                .groupName("default")

                //在生成的 Swagger 文档中，所有的请求路径都会添加 /api 这个前缀
                // Swagger3 中已经过时，推荐使用其他方式来设置路径映射。例如，你可以在全局的 Swagger 配置文件中使用 servers()
                //.pathMapping("/")

                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())

                // 接口调试地址
                .host("localhost")

                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()

                // 支持的通讯协议集合
                .protocols(new HashSet(Arrays.asList("https", "http")))

                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(securitySchemes())

                // 授权信息全局应用
                .securityContexts(securityContexts());
        return docket;
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(applicationName + " Api Doc")
                .description("order 模块服务")
                .contact(new Contact("wang zhiWen","15377597227" , "3035361676@qq.com"))
                .version("Application Version: " + "1.0.0" + ", Spring Boot Version: " + SpringBootVersion.getVersion())
                .build();
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("BASE_TOKEN", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(
                                new SecurityReference("BASE_TOKEN",
                                        new AuthorizationScope[]{new AuthorizationScope("global", "")})
                                )
                        )
                        .build()
        );
    }

}
