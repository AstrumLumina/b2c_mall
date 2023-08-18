package com.wzw.b2cmall.product.config;


import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

@Configuration
public class SwaggerConfig {

    @Value("${server.port}")
    private String serverport;
    @Value("${spring.application.name: b2cmall-pms}")
    private String applicationName;
    @Value("${server.servlet.context-path}")
    private String serverContextPath;
    @Value("${costum.swagger.gateway-application-name}")
    private String gatewayApplicationName;
    @Value("costum.selfServicePath")
    private String selfServicePath;

    @Autowired
    DiscoveryClient discoveryClient;



    private int servserCount;
    private int addedServer;

    @Bean
    public Docket createRestApi() {
//        LinkedList<ServerVariable> serverVariables = new LinkedList<>();
//        LinkedList<VendorExtension> vendorExtensions = new LinkedList<>();
//        Server[] servers=new Server[4];
//        if (selfServicePath!=null&&!selfServicePath.equals("")){
//            servers[0]=new Server("",selfServicePath,"服务外部访问地址",serverVariables,vendorExtensions);
//        }
//        servers[1]=new Server("localhostAddr","http://localhost"+serverport,"本地访问地址",serverVariables,vendorExtensions);
//
//        for (ServiceInstance instance : discoveryClient.getInstances(gatewayApplicationName)) {
//            servers[2]=new Server("addrFromGateway:"+gatewayApplicationName+":"+instance.getInstanceId(),instance.getUri().getPath(),"通过网关访问问地址",serverVariables,vendorExtensions);
//            break;
//        }
//        for (ServiceInstance instance : discoveryClient.getInstances(applicationName)) {
//            servers[3]=new Server("addrFromDiscoverService:"+":"+instance.getInstanceId(),instance.getUri().getPath(),"通过注册中心获取的访问地址",serverVariables,vendorExtensions);
//            break;
//        }

//        for (ServiceInstance instance : discoveryClient.getInstances(gatewayApplicationName)) {
//            if (instance.isSecure()){
//                servers[2]=new Server("addrFromGateway:"+gatewayApplicationName+":"+instance.getInstanceId(),instance.getUri().getPath(),"通过网关访问问地址",serverVariables,vendorExtensions);
//                break;
//            }
//        }
//        for (ServiceInstance instance : discoveryClient.getInstances(applicationName)) {
//            if (instance.isSecure()){
//                servers[3]=new Server("addrFromDiscoverService:"+":"+instance.getInstanceId(),instance.getUri().getPath(),"通过注册中心获取的访问地址",serverVariables,vendorExtensions);
//                break;
//            }
//        }
        Docket docket = new Docket(DocumentationType.OAS_30)
                .groupName("default")
               // .servers(servers[0],servers[1],servers[2],servers[3])

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
                .paths(PathSelectors.ant(serverContextPath+ "/test/**").negate())
                .build()

                // 支持的通讯协议集合
                .protocols(new HashSet(Arrays.asList("https", "http")))

                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(securitySchemes())

                // 授权信息全局应用
                .securityContexts(securityContexts());
        return docket;
    }


    @Bean
    public Docket createTestRestApi() {

        Docket docket = new Docket(DocumentationType.OAS_30)
                .groupName("testGroup")

                //在生成的 Swagger 文档中，所有的请求路径都会添加 /api 这个前缀
                // Swagger3 中已经过时，推荐使用其他方式来设置路径映射。例如，你可以在全局的 Swagger 配置文件中使用 servers()
                //.pathMapping("/")

                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())

                // 接口调试地址
                .host("localhost")

                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wzw.b2cmall.product.controller.test"))
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
                .description("product 模块服务")
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
