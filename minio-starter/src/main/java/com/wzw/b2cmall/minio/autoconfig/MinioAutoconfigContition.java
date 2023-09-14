package com.wzw.b2cmall.minio.autoconfig;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MinioAutoconfigContition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean enableAutoconfig = Boolean.parseBoolean(context.getEnvironment().getProperty("minio.enableAutoconfig"));
        boolean hasEnableMinioClient = context.getBeanFactory().getBeansWithAnnotation(EnableMinioClient.class).size() > 0;
        return enableAutoconfig || hasEnableMinioClient;
    }
}
