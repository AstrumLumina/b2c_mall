package com.wzw.b2cmalll.gateway.component;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class MyCorsWebFilter extends CorsWebFilter {
    protected final CorsConfigurationSource configSource;
    protected final List<String> excludePaths;
    protected final AntPathMatcher antPathMatcher;
    protected final CorsProcessor processor;


    public MyCorsWebFilter(CorsConfigurationSource configSource, List<String> excludePaths){
        this(configSource,new DefaultCorsProcessor(),excludePaths);
    }

    public MyCorsWebFilter(CorsConfigurationSource configSource, CorsProcessor processor, List<String> excludePaths) {
        super(configSource,processor);
        this.configSource=configSource;
        this.excludePaths=excludePaths;
        this.processor=processor;
        this.antPathMatcher=new AntPathMatcher();
    }



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        boolean isPreFlightRequest=CorsUtils.isPreFlightRequest(request);
        /*
         * 先将不需要拦截的路径注册一个做任何处理的cors拦截器,以便排除掉该路劲的cors过滤
         * */
        for (int i = 0; i < excludePaths.size(); i++) {
            boolean mached=antPathMatcher.match(excludePaths.get(i),path);
            if (antPathMatcher.match(excludePaths.get(i),path)){
                if (!isPreFlightRequest){
                    return chain.filter(exchange);
                }else {
                    break;
                }
            }
        }
        CorsConfiguration corsConfiguration = this.configSource.getCorsConfiguration(exchange);
        boolean isValid = this.processor.process(corsConfiguration, exchange);
        return isValid && !isPreFlightRequest ? chain.filter(exchange) : Mono.empty();
    }
}
