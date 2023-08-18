package com.wzw.b2cmall.product.controller.test;

import com.wzw.b2cmall.product.feign.member.UserService;
import com.wzw.b2cmall.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Api(value = "测试接口",tags = "测试")
@RestController()
@RequestMapping("/test")
@RefreshScope  //需要设置此注解,才会自动更新
public class TestController {
    @Autowired
    private UserService userService;

    //测试nacos配置中心
    @Value("${product.configTest: undefined}")
    private String configTest;

    //测试nacos配置中心  user.name自己的配置无效,使用的是操作系统的user.name
    @Value("${product.user.name: undefined}")
    private String userName;



    @ApiOperation(value = "远程过程调用测试",
                  notes = "测试open-feign 通过nacos获取的服务地址 远程调用member服务")
    @GetMapping("/user/info/{id}")
    public R getSelfUserInfo(@PathVariable("id") Long id){
       return userService.getInfo(id);
    }


    @ApiOperation(notes = "测试通过nacos配置中心获取配置",
                  value = "配置中心测试")
    @GetMapping("/config/getinfo")
    public R getNacosConfig(){
        R r = new R();
        r.put("product.configTest",configTest);
        r.put("product.user.name",userName);
        return r;
    }


}
