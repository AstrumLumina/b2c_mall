package com.wzw.b2cmalll.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CrosFilterConfig {

   /*
   在 Spring Cloud Gateway 中，你可以通过将过滤器注册为 Spring Bean
    的方式来实现对 Web Filter 的配置。这是因为 Spring Cloud Gateway
    在其内部实现中使用了 Spring WebFlux，而 Spring WebFlux 基于响应式编程模型，
    支持将 Web Filter 注入为 Spring Bean。
   相比之下，Spring Boot Web是基于传统的 Servlet 编程模型，过滤器的注册方式与
   Spring Cloud Gateway 不同。在 Spring Boot Web 中，过滤器的注册通常是通过
    FilterRegistrationBean 或者 @ServletComponentScan 注解来实现。这是由于
     Spring Boot Web 使用了 Servlet 容器（如 Tomcat、Jetty 等），
     而这些容器在处理过滤器时需要按照 Servlet 规范的方式进行配置。
*/
   @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        return new  CorsWebFilter(urlBasedCorsConfigurationSource);
    }

}


