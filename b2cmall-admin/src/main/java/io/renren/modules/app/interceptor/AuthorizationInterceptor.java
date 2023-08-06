/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.interceptor;


import io.jsonwebtoken.Claims;
import io.renren.common.exception.RRException;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.app.annotation.Login;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

    public static final String USER_KEY = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        /*
        * 在Spring框架中，HandlerMethod是一个类，它是用来表示处理HTTP请求的方法。
        * 它是Spring MVC框架中的一个关键类，
        * 用于将请求映射到具体的处理方法上，并执行相应的逻辑。
        HandlerMethod类的作用是封装了一个处理请求的方法，包括方法本身、
        * 方法所属的控制器类对象以及方法的相关信息，如方法参数、返回值类型等。
        * 通过HandlerMethod类，可以方便地获取到处理方法的各种信息，
        * 进而进行权限校验、参数处理、返回值处理等操作。
        在Spring MVC框架中，当一个请求到达时，框架会根据请求的URL路径匹配到对应的HandlerMethod，
        * 然后调用该HandlerMethod来处理请求。HandlerMethod可以通过一些注解（如@RequestMapping、
        * @GetMapping等）来指定请求的路径和HTTP方法，并通过方法参数来接收请求的数据，
        * 最后将处理结果返回给客户端。
        总的来说，HandlerMethod在Spring框架中扮演着连接请求和处理方法之间的桥梁的角色，
        * 通过封装和管理处理方法，使得请求能够被正确地分发和处理。
* */
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        }else{
            return true;
        }

        if(annotation == null){
            return true;
        }

        //获取用户凭证
        String token = request.getHeader(jwtUtils.getHeader());
        if(StringUtils.isBlank(token)){
            token = request.getParameter(jwtUtils.getHeader());
        }

        //凭证为空
        if(StringUtils.isBlank(token)){
            throw new RRException(jwtUtils.getHeader() + "不能为空", HttpStatus.UNAUTHORIZED.value());
        }

        Claims claims = jwtUtils.getClaimByToken(token);
        if(claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
            throw new RRException(jwtUtils.getHeader() + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, Long.parseLong(claims.getSubject()));

        return true;
    }
}
