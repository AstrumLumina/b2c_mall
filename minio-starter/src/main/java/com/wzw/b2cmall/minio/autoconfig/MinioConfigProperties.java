package com.wzw.b2cmall.minio.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;


@ConfigurationProperties(prefix = "minio.server")
@Data
//@RefreshScope  //ConfigurationProperties默认就是动态刷新的,不需要使用该注解也可以
public class MinioConfigProperties {

    /*
    * minio服务器的地址,或者minio集群的负载均衡代理访问地址
    * */
    private String endpoint;
    /*
    * 用户名 默认值 minio
    * */
    private String accesKey="minio";
    /*
    * 密码 默认值 password
    * */
    private String secrectKey="password";
    /*
     * 默认访问的桶名称
     * */
    private String bucketName;
    /*
    * 默认下载签名url过期时间,默认时间单位 分钟 默认值 60分钟
    * */
    private int downloadSignatureExpire=60;
    /*
     * 默认上传签名url过期时间,默认时间单位 分钟 默认值 10分钟
     * */
    private int uploadSignatureExpire=10;
    /*
    * 上传文件时,默认文件存储在按照该指定格式下格式化上传日期后的字符串 对应的的文件夹中
    * */
    private String uploadFileDataFolderFormat="yyyy-MM-dd";



}
