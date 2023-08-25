package com.wzw.b2cmalll.gateway.component;


import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * swagger的配置类 改动默认的 inMemorySwaggerResourcesProvider
 *
 * @author teler
 */
@Component
@Primary  //(可能)swagger自己往容器中添加了此类别的配置类,因此将自己的类作为注入的首选项 ,防止注入springfox自己的 inMemorySwaggerResourcesProvider
@Log4j
public class MySwaggerResourcesProvider implements SwaggerResourcesProvider{
    /**
     * swagger默认的url后缀
     */
    private static final String SWAGGER_RESOURCE_API_URI = "/swagger-resources";


    /**
     * gatewayProperties：gatewayProperties是一个配置类，用于配置Spring Cloud Gateway的全局属性和行为。
     * 这个类通常用于设置一些全局的默认值和配置项，以控制网关的行为。gatewayProperties类中定义了一系列属性，
     * 用于配置路由和过滤器的默认值、超时时间、响应头处理、路径重写等。
     * 这些属性可以在application.yaml或application.properties配置文件中进行设置，
     * 也可以通过@ConfigurationProperties进行注入。
     */
    private final GatewayProperties gatewayProperties; //这个只能获取配置文件中的信息
    /**
     * RouteLocator：RouteLocator是一个接口，在Spring Cloud Gateway中用于定义和配置路由规则。
     * 通过实现RouteLocator接口，你可以编码方式或配置方式定义路由规则，并将其应用于网关的路由配置。
     * RouteLocator主要负责动态处理路由规则的创建、修改和删除。
     */
    //这是一个接口，flux异步接口,开始其中的route缓存为空，当调用.getRoutes().subscribe来消费路由时，
    // 会获取到全部的路由信息并缓存，包括配置文件中的配置以及代码配置的路由
    private final RouteLocator routeLocator;

//    @Autowired
//    private WebClient.Builder loadbalanceWebclientBuilder;
    @Value("${costum.selfServicePath}")
    private String selfServicePath;
    @Value("spring.application.name")
    private String applicationName;

    @Autowired
    @Qualifier(value = "commonWebClient")
    private WebClient commonWebClient;
    @Autowired
    private  DiscoveryClient discoveryClient;

    @Value("${costum.swagger.resource.getSwaggerResourceFromServiceDiscover}")
    private boolean SwaggerResourceFromServiceDiscover=false;
    @Value("${costum.swagger.resource.getSwaggerResourceFromGateWayRoute}")
    private  boolean SwaggerResourceFromGateWayRoute =true;


    @Autowired
    public MySwaggerResourcesProvider(GatewayProperties gatewayProperties, RouteLocator routeLocator) {
        this.gatewayProperties = gatewayProperties;
        this.routeLocator = routeLocator;
    }

//    List<SwaggerResource> checkedResources = new ArrayList<>();
//    List<String> checkedRouteIds = new ArrayList<>();
//    List<String> checkedHosts=new LinkedList<>();

    List<SwaggerResource> checkedResources = new ArrayList<>();
    List<String> checkedRouteIds = new ArrayList<>();
    List<String> checkedHosts=new LinkedList<>();

    private long lastAccessTime=0L;
    private final long experTime=1000*3;//5s后如果在访问则更新一次swagger资源
    /*
    * 译器对受检异常的检查：使用SneakyThrows注解可以告诉编译器，虽然代码中抛出了受检异常，但是无需进行强制的异常处理。
    * 这样一来，我们就可以在方法中抛出受检异常，而不需要显式地在方法签名中声明或者进行异常处理。
    * 将受检异常转换为非受检异常：通过使用SneakyThrows注解，受检异常会被转换为非受检异常（也就是运行时异常）。
    * 这意味着，即使在方法中抛出受检异常，也不会导致调用方法的代码在编译时强制要求进行异常处理。
    * 需要注意的是，SneakyThrows注解主要用于简化代码，但并不推
* */
    //@SneakyThrows
    @Override
    public List<SwaggerResource> get() {
        //判断是否需要更新
        Date now = new Date();
        if (now.getTime()-lastAccessTime<experTime){
            return checkedResources;
        }else {
            lastAccessTime=now.getTime();

            checkedResources.clear();
            checkedRouteIds.clear();
            checkedHosts.clear();
            if (SwaggerResourceFromGateWayRoute){
                getSwaggerResourceGatewayRoute();
            }
            if (SwaggerResourceFromServiceDiscover){
                getSwaggerResourceFromServiceDiscover();
            }
//            //没有效果,客户端获取不到刷新即可
//            try {
//                Thread.sleep(10); //由于这里是异步获取swaggerresource,可能swaggesource还未初始化就返回了,这里暂停一下,防止有资源任然返回空
//            } catch (InterruptedException e) {
//            }
            return checkedResources;
        }
    }


    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }

    private List<SwaggerResource> getSwaggerResourceFromGateWay(){
        return null;
    }
    private void checkAndAddSwaggerResourceFromPossibleRoute(URI possibleRouteURI,String possibleRouteId,String gatewayPredicatePath){
        checkedRouteIds.add(possibleRouteId);
        String host = possibleRouteURI.getHost();
        log.debug("check route host: "+host);
        if (!checkedHosts.contains(host)){
            checkedHosts.add(host);
            commonWebClient
                    .get()
                    .uri(selfServicePath +gatewayPredicatePath+ SWAGGER_RESOURCE_API_URI)
                    .retrieve()
                    .bodyToFlux(SwaggerResource.class)
                    .collectList()
                    .subscribe(swaggerResources -> {
                        if (swaggerResources.size()>0){
                            SwaggerResource possibleSwaggerResource = swaggerResources.get(0);
                            String tempSwaggerResourceName = possibleRouteURI.getHost() + ":" + possibleSwaggerResource.getName();
                            for (SwaggerResource checkedResource : checkedResources) {
                                if (checkedResource.getName()==tempSwaggerResourceName){
                                    return;
                                }
                            }
                            swaggerResources.forEach(swaggerResource -> {
                                swaggerResource.setUrl(gatewayPredicatePath+getSwaggerResourceUrlSuffix(swaggerResource.getUrl()));
                                swaggerResource.setName(possibleRouteURI.getHost() + ":" + swaggerResource.getName());
                            });
                            log.debug("debug:  add swaggerResources: "+swaggerResources.size());
                            checkedResources.addAll(swaggerResources);
                        }
                    });

        }

}

    private void getSwaggerResourceGatewayRoute(){
        Disposable subscribe = routeLocator
                .getRoutes()
                .subscribe(route -> {
                    checkAndAddSwaggerResourceFromPossibleRoute(route.getUri(),route.getId(),getGatewayUriPrefix(route.getPredicate().toString()));
                });
        // 遍历配置文件中配置的所有服务
        gatewayProperties
                .getRoutes()
                .stream()
                // 过滤已经检测过的服务
                .filter(routeDefinition -> checkedRouteIds.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> {
                    List<PredicateDefinition> paths = routeDefinition
                            .getPredicates()
                            .stream()
                            .filter(predicateDefinition -> predicateDefinition.getName().equalsIgnoreCase("Path"))
                            .limit(1L)
                            .collect(Collectors.toList());
                    String temp="";
                    if (paths.size()>0){
                        temp = paths.get(0).getArgs().get(NameUtils.GENERATED_NAME_PREFIX+"0").replace("**","");
                    }
                    checkAndAddSwaggerResourceFromPossibleRoute(routeDefinition.getUri(),routeDefinition.getId(),temp);
                });

    }

    private void getSwaggerResourceFromServiceDiscover(){
        List<String> services = discoveryClient.getServices();
        services.forEach(service->{
            checkedHosts.add("lb://"+service);
            if (!checkedHosts.contains("lb://"+service)&&!checkedHosts.contains(service)&&!service.equals(applicationName)){
                getSwaggerSourceFromOneServiceInstance(discoveryClient.getInstances(service),0,service);
            }
        });
    }
    private void getSwaggerSourceFromOneServiceInstance(List<ServiceInstance> serviceInstances,int currentInstanceIndex,String servicename){
        if (currentInstanceIndex>=serviceInstances.size()){
            return ;
        }
        commonWebClient
                .get()
                .uri(serviceInstances.get(currentInstanceIndex).getUri()+ SWAGGER_RESOURCE_API_URI)
                .retrieve()
                .bodyToFlux(SwaggerResource.class)
                .collectList()
                .doOnSuccess( swaggerResources->{
                    swaggerResources.forEach(swaggerResource -> {
                        swaggerResource.setName(servicename+":"+swaggerResource.getName());
                        swaggerResource.setUrl("/"+servicename+swaggerResource.getUrl());  //不太靠谱,最好还是禁用,根据gateway来获取
                    });
                    checkedResources.addAll(swaggerResources);
                })
                .doOnError(throwable -> {getSwaggerSourceFromOneServiceInstance(serviceInstances,currentInstanceIndex+1,servicename);})
                .subscribe();
    }

    private Pattern findGatewayUriPrefixPattern = Pattern.compile("Paths: \\[(.*?)\\]");
    private Pattern findSwagger2UrlSuffixPattern = Pattern.compile("(/v2/api-docs.*)$");
    private Pattern findSwagger3UrlSuffixPattern = Pattern.compile("(/v3/api-docs.*)$");
    /*
    Paths: [/example/**], match trailing slash: true
    提取出上面的 path值
     */
    private String getGatewayUriPrefix(String predicate){
        Matcher matcher = findGatewayUriPrefixPattern.matcher(predicate);
        if (matcher.find()){
            String s = matcher.group(1).replaceFirst("\\*\\*", "");
            return s;
        }
        return null;
    }
    /*
    提取出swagger api 文档的地址
     */
    private String getSwaggerResourceUrlSuffix(String swaggerResourceUrl){
        Matcher matcher = findSwagger2UrlSuffixPattern.matcher(swaggerResourceUrl);
        if (matcher.find()){
            return matcher.group(0);
        }else {
            matcher= findSwagger3UrlSuffixPattern.matcher(swaggerResourceUrl);
            if (matcher.find()){
                return matcher.group(0);
            }
        }
        return "";
    }


}