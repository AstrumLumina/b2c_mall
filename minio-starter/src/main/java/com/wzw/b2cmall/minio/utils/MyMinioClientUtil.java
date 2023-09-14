package com.wzw.b2cmall.minio.utils;


import com.wzw.b2cmall.minio.autoconfig.MinioConfigProperties;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PostPolicy;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("myMinioClientUtil")
@RefreshScope
public class MyMinioClientUtil {
    private static MinioConfigProperties minioConfigProperties;
    private static DateFormat dateFormat;
//    private static Map<String,String> defaultExtraQueryParams;
//
//    static {
//        defaultExtraQueryParams=new HashMap<>();
//        defaultExtraQueryParams.put("response-content-type","application/octet-stream");
//        defaultExtraQueryParams.put("content-type","application/octet-stream");
//    }


    @Autowired //必须要将该类注入到容器中才会调用构造方法,才会初始化静态属性
    MyMinioClientUtil(MinioConfigProperties minioConfigProperties){
        MyMinioClientUtil.minioConfigProperties=minioConfigProperties;
        MyMinioClientUtil.dateFormat=new SimpleDateFormat(minioConfigProperties.getUploadFileDataFolderFormat());
    }

    public static MinioConfigProperties getMinioConfigProperties() {
        return minioConfigProperties;
    }
    public static DateFormat getDateFormat() {
        return dateFormat;
    }


    public static String getPresignedDownloadObjectUrl(@NotNull MinioClient minioClient, @NotEmpty String bucketName, String downloadObjectName, int expire, TimeUnit timeUnit){
        try {
            log.info("try to get presigned download url for "+downloadObjectName+" from "+bucketName +" bucket; with properties: expire="+expire+" "+timeUnit.name());
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(downloadObjectName)
                            .expiry(expire, timeUnit)
                            //.extraQueryParams(defaultExtraQueryParams)
                            .build());
        } catch (Exception e){ //需要抛出异常以便感知到错误,捕获再抛出因为直接抛出异常太多,不好看
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage(),e);
        }
    }
    public static String getPresignedDownloadObjectUrl(@NotNull MinioClient minioClient, @NotEmpty String bucketName, String downloadObjectName){
        return MyMinioClientUtil.getPresignedDownloadObjectUrl(minioClient,bucketName,downloadObjectName,minioConfigProperties.getDownloadSignatureExpire(),TimeUnit.SECONDS);
    }

    public static String getPresignedPutUploadObjectUrl(@NotNull MinioClient minioClient, @NotEmpty String bucketName,String uploadObjectName,int expire,TimeUnit timeUnit){
        try {
            log.info("try to get presigned put upload url for "+uploadObjectName+" to "+bucketName +" bucket ; with properties: expire="+expire+" "+timeUnit.name());
            return  minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(uploadObjectName)
                            .expiry(expire, timeUnit)
                            //.extraQueryParams(defaultExtraQueryParams)
                            .build());
        } catch (Exception e){ //需要抛出异常以便感知到错误,捕获再抛出因为直接抛出异常太多,不好看
        log.error(e.getMessage());
        throw new RuntimeException(e.getMessage(),e);
    }

    }
    public static String getPresignedPutUploadObjectUrl(@NotNull MinioClient minioClient, @NotEmpty String bucketName,String uploadObjectName){
       return MyMinioClientUtil.getPresignedPutUploadObjectUrl(minioClient,bucketName,uploadObjectName,minioConfigProperties.getUploadSignatureExpire(),TimeUnit.SECONDS);
    }

    public static Map<String, String>  getPresignedPostFormData(@NotEmpty MinioClient minioClient, @NotEmpty String bucketName,String uploadObjectName,int expire,TimeUnit timeUnit){
        try {
            log.info("try to get presigned post upload url for "+uploadObjectName+" to "+bucketName +" bucket; with properties: expire="+expire+" "+timeUnit.name());
            // 生成策略
            ZonedDateTime expirationData = ZonedDateTime.now().plus(Duration.ofMinutes(expire));
            PostPolicy policy = new PostPolicy(bucketName, expirationData);
            //policy.addEqualsCondition("Content-Type", "application/octet-stream");
            policy.addEqualsCondition("key", uploadObjectName);

            // 生成预签名POST URL和策略
            Map<String, String> presignedPostFormData = minioClient.getPresignedPostFormData(policy);
            //presignedPostFormData.put("content-type","application/octet-stream");
            return presignedPostFormData;

        } catch (Exception e){ //需要抛出异常以便感知到错误,捕获再抛出因为直接抛出异常太多,不好看
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage(),e);
        }

    }
    public static Map<String, String>  getPresignedPostFormData(@NotEmpty MinioClient minioClient, @NotEmpty String bucketName,String uploadObjectName){
        return MyMinioClientUtil.getPresignedPostFormData(minioClient, bucketName,uploadObjectName,minioConfigProperties.getUploadSignatureExpire(),TimeUnit.SECONDS);
    }


    /*
     * 生产文件上传的路径,默认保存到上传日文件加下
     * */
    public static String generateObjectNameForFile(String fileName, DateFormat dateFormat){
        UUID uuid = UUID.randomUUID();
        uuid.toString();
        return dateFormat.format(new Date())+"/"+ UUID.randomUUID()+fileName;
    }
    public static String generateObjectNameForFile(String fileName){
        return MyMinioClientUtil.generateObjectNameForFile(fileName, MyMinioClientUtil.dateFormat);
    }

}
