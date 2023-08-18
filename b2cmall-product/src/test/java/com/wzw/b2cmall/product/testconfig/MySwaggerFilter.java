package com.wzw.b2cmall.product.testconfig;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component //该竹节不能省略,否则配置无效,因为容器加载顺序,需要先将该bean加载到spring容器中再取出用于初始化web容器
@WebFilter(filterName = "swaggertestfilter",urlPatterns = "/**")
public class MySwaggerFilter  implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (((HttpServletRequest)request).getRequestURI().equals("/swagger-resources")){
            ((HttpServletRequest) request).getMethod();//无效调用便于暂停测试
        }
        //继续执行下一个过滤器
        chain.doFilter(request, response);

    }
}
