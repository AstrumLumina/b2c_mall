package com.wzw.b2cmall.resource.config;


import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.cloud.client.SpringCloudApplication;
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
    @Value("${spring.application.name: b2cmall-pms}")
    private String applicationName;
    @Value("${server.servlet.context-path}")
    private String serverContextPath;

    private ApiInfo defaultApiInfo=new ApiInfoBuilder()
            .title(applicationName+" 服务模块")
            .contact(new Contact("wang zhiWen","15377597227","3035161676@qq.com"))
            .version("Application Version: "+"1.0.0 "+"SpringBoot Version: "+ SpringBootVersion.getVersion())
            .build();

    private List<SecurityScheme> defaultSecuritySchemes=Collections.singletonList(new ApiKey("BASE_TOKEN","token", In.HEADER.toValue()));
    private List<SecurityContext> defaultSecurityContext=Collections.singletonList(
      SecurityContext
              .builder()
              .securityReferences(
                      Collections.singletonList(new SecurityReference("BASE_TOKEN",
                              new AuthorizationScope[]{new AuthorizationScope("globale","全局应用token请求头")}))
              )
              .build()
    );

    @Bean
    public Docket createDefaultRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("default")
                .apiInfo(defaultApiInfo)
                .securitySchemes(defaultSecuritySchemes)
                .securityContexts(defaultSecurityContext)
                .protocols(new HashSet<>(Arrays.asList("https","http")))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant(serverContextPath+"/test/**").negate())
                .build();
    }

    @Bean
    public Docket createTestRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("test")
                .apiInfo(defaultApiInfo)
                .securitySchemes(defaultSecuritySchemes)
                .securityContexts(defaultSecurityContext)
                .protocols(new HashSet<>(Arrays.asList("https","http")))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wzw.b2cmall.resource.controller.test"))
                .paths(PathSelectors.any())
                //.apis(RequestHandlerSelectors.any())
                //.paths(PathSelectors.ant(serverContextPath+"/test/**"))
                .build();
    }


}
