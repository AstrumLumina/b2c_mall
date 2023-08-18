package com.wzw.b2cmalll.gateway.testconfig;



import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


//@Component
//@Order(-1)
public class MySwaggerApidocsFilter implements WebFilter {

    private ForwardedHeaderFilter forwardedHeaderFilter=new ForwardedHeaderFilter();

    private String[] SWAGGER_APIDOCS_PATHS_PATTERN_TO_FILTER={".*/v2/api-docs((?<= \\?).*)?$",".*/v3/api-docs((?<= \\?).*)?$"};

    private Pattern[] SWAGGER_APIDOCS_PATTERNS={
                    Pattern.compile(SWAGGER_APIDOCS_PATHS_PATTERN_TO_FILTER[0]),
                    Pattern.compile(SWAGGER_APIDOCS_PATHS_PATTERN_TO_FILTER[1])
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        for (Pattern swagger_apidocs_pattern : SWAGGER_APIDOCS_PATTERNS) {
            Matcher matcher = swagger_apidocs_pattern.matcher(path);
            if (matcher.find()){
                try {
                    forwardedHeaderFilter.doFilter((ServletRequest) exchange.getRequest(), (ServletResponse) exchange.getResponse(), new FilterChain() {
                        @Override
                        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
                            return;
                        }
                    });
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return chain.filter(exchange);
    }
}
