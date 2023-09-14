package com.wzw.b2cmall.minio.autoconfig;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

@ImportAutoConfiguration(MinioClientAutoConfiguration.class)
public @interface EnableMinioClient {

}
