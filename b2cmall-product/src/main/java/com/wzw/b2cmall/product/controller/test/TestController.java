package com.wzw.b2cmall.product.controller.test;

import com.wzw.b2cmall.product.feign.member.UserService;
import com.wzw.b2cmall.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Api(value = "测试接口",tags = "测试")
@RestController()
@RequestMapping("/test")
@RefreshScope  //需要设置此注解,才会自动更新
@Slf4j
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

    @ApiOperation(notes = "测试文件下载功能",
            value = "文件下载测试")
    @GetMapping("/download/qq")
    public ResponseEntity<InputStreamResource> downloadQQFile(String fileName)
            throws IOException {
        log.info("进入下载方法...");
        //读取文件
        String filePath = "D:\\lenovo浏览器下载\\" + "QQ_6.8.2.21241_966_EXP" + ".dmg";
        FileSystemResource file = new FileSystemResource(filePath);
        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    @ApiOperation(notes = "测试文件下载功能",
            value = "文件下载测试")
    @GetMapping("/download/clearapp")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadFileClearApp(String fileName)
            throws IOException {
        log.info("进入下载方法...");
        //读取文件
        String filePath = "D:\\lenovo浏览器下载\\" + "BleachBit-4.4.2-portable" + ".zip";
        FileSystemResource file = new FileSystemResource(filePath);
        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }
}
