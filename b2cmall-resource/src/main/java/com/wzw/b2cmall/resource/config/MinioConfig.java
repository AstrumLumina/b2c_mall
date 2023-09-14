package com.wzw.b2cmall.resource.config;

import com.wzw.b2cmall.minio.autoconfig.EnableMinioClient;
import com.wzw.b2cmall.minio.utils.MinioBucketTemplate;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableMinioClient
public class MinioConfig {
    @Autowired
    private MinioClient minioClient;

//    @Bean
//    @RefreshScope
    public MinioBucketTemplate productCategoryIconMinioBucketTemplate(){
        return new MinioBucketTemplate()
                .setMinioClient(minioClient)
                .setBucketName("b2cmall.product.category")
                .setMinioServerUrl("http://10.0.0.208:9000");

    }
}
