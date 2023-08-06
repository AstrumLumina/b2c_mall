/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.resolver;

import io.renren.modules.app.annotation.LoginUser;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.interceptor.AuthorizationInterceptor;
import io.renren.modules.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 * @author Mark sunlightcs@gmail.com
 */

/*
* HandlerMethodArgumentResolver是一个接口，它是Spring
* MVC框架中用来处理方法参数的类的统一抽象。它定义了一组规范，
* 用于解析请求中的参数，并将其转换为方法所需的参数类型。
具体而言，HandlerMethodArgumentResolver的作用是将请求中的参数值解析为方法参数对象，
* 以便Spring MVC框架可以将解析后的参数传递给对应的HandlerMethod进行处理。
实现了HandlerMethodArgumentResolver接口的类可以通过自定义实现来支持特定的参数类型或处理逻辑。
* 当Spring MVC框架需要处理请求时，会调用注册的HandlerMethodArgumentResolver的实现类来解析方法参数。
通过自定义HandlerMethodArgumentResolver，开发者可以实现自己的参数解析逻辑，
* 例如从请求中获取特定的Header信息、从请求体中解析JSON数据等等。这样可以极大地增强Spring
* MVC框架的灵活性和扩展性。
需要注意的是，HandlerMethodArgumentResolver只负责解析参数，并不负责验证或处理参数的合法性。
* 在参数解析完成后，开发者可以自行添加验证逻辑来确保参数的合法性。
* */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserEntity.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID  jwt拦截验证是注入到请求域中
        Object object = request.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if(object == null){
            return null;
        }

        //获取用户信息
        UserEntity user = userService.getById((Long)object);

        return user;
    }
}
