package com.wzw.b2cmall.product.testconfig;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求进入控制器之前执行
        String uri = request.getRequestURI();

        if (uri.equals("/swagger-resources")) {
            // 拦截到/v3/api-docs请求，进行相关处理
            //拦截到请求,继续查看会访问那些方法
            return true; //便于跟踪请求进行调试验证,并不拦截
        }

        return true; // 返回true表示放行请求
    }
}