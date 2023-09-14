package com.wzw.b2cmall.minio.autoconfig;


import com.wzw.b2cmall.minio.utils.MinioBucketTemplate;
import com.wzw.b2cmall.minio.utils.MyMinioClientUtil;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.*;

@Configuration
@Conditional(MinioAutoconfigContition.class)
@EnableConfigurationProperties(MinioConfigProperties.class)  //使 使用 @ConfigurationProperties 注解的类生效
@Import(MyMinioClientUtil.class) //@Import用于导入配置类,该类上需要有@component等标识该类为一个bean时才会往容器中注入该类
public class MinioClientAutoConfiguration {
    @Autowired
    MinioConfigProperties minioConfigProperties;

    @Bean("defaultMinioClient") //只有该bean注入成功了,才会注入默认的MinioBucketTemplate
    @ConditionalOnMissingBean
    @RefreshScope
    public MinioClient defaultMinioClient(){
        return  MinioClient.builder()
                .endpoint(minioConfigProperties.getEndpoint())
                .credentials(minioConfigProperties.getAccesKey(), minioConfigProperties.getSecrectKey())
                .build();
    }

    @Bean("defaultMinioBucketTemplate")
    @ConditionalOnBean(name = "defaultMinioClient")
    @Conditional(DefaultMinioBucketTemplateCondition.class)
    @ConditionalOnMissingBean
    @RefreshScope
    public MinioBucketTemplate defaultMinioBucketTemplate(@Autowired @Qualifier("defaultMinioClient") MinioClient minioClient){
        return new MinioBucketTemplate()
                .setMinioClient(minioClient)
                .setBucketName(minioConfigProperties.getBucketName())
                .setDownloadSignatureExpire(minioConfigProperties.getDownloadSignatureExpire())
                .setUploadSignatureExpire(minioConfigProperties.getUploadSignatureExpire())
                .setUploadFileDataFolderFormat(minioConfigProperties.getUploadFileDataFolderFormat())
                .setMinioServerUrl(minioConfigProperties.getEndpoint());
    }
}
