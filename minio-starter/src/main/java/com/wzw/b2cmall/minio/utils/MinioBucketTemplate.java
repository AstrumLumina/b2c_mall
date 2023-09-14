package com.wzw.b2cmall.minio.utils;

import com.wzw.b2cmall.minio.autoconfig.MinioConfigProperties;
import io.minio.MinioClient;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Data
@Accessors(chain = true)
@Slf4j
@DependsOn("myMinioClientUtil") //该注解不生效
public class MinioBucketTemplate {
    @NotNull
    private MinioClient minioClient;
    @NotNull
    private String bucketName;

    private int downloadSignatureExpire;
    private int uploadSignatureExpire;
    private String uploadFileDataFolderFormat;
    private DateFormat dateFormat;
    private String minioServerUrl;


//    //toDependOnMyMinioClientUtils: 这个会在init 方法之前执行,触发 MyMinioClientUtils的注入从而初始化
//    //在方法体中一定要使用一下myMinioClientUtil,否则容易存在被编译器优化导致无法正确处理,
//    //导致MinioBucketTemplate 在 MyMinioClientUtil之前注入到容器中产生空指针异常
//    //使用注解@DependsOn("myMinioClientUtil") 实现更加更加好
    @Autowired
    public void setMyMinioClientUtils(MyMinioClientUtil myMinioClientUtil){
        log.debug("MyMinioClientUtils was autowired(this will trigger the refresh and new of MyMinioClientUtils.class)");
    }


    @PostConstruct //该初始化方法不能带参数
    private void init(){
        System.out.println(" MinioBucketTemplate postconstruct ");
        System.out.println(MyMinioClientUtil.getMinioConfigProperties());


        MinioConfigProperties minioConfigProperties= MyMinioClientUtil.getMinioConfigProperties();
        if (!StringUtils.hasLength(uploadFileDataFolderFormat)){
            uploadFileDataFolderFormat=minioConfigProperties.getUploadFileDataFolderFormat();
        }
        if (downloadSignatureExpire<=0){
            downloadSignatureExpire=minioConfigProperties.getDownloadSignatureExpire();
        }
        if (uploadSignatureExpire<=0){
            uploadSignatureExpire=minioConfigProperties.getUploadSignatureExpire();
        }
        dateFormat=new SimpleDateFormat(this.uploadFileDataFolderFormat);
    }

    public  String getPresignedDownloadObjectUrl(String downloadObjectName, int downloadSignatureExpire, TimeUnit timeUnit){

       return MyMinioClientUtil.getPresignedDownloadObjectUrl(minioClient,bucketName,downloadObjectName,downloadSignatureExpire,timeUnit);
    }
    public  String getPresignedDownloadObjectUrl(String downloadObjectName){
        return this.getPresignedDownloadObjectUrl(downloadObjectName,downloadSignatureExpire,TimeUnit.SECONDS);
    }

    public String getPresignedPutUploadObjectUrl(String uploadObjectName,int expire,TimeUnit timeUnit){
        return MyMinioClientUtil.getPresignedPutUploadObjectUrl(minioClient,bucketName,uploadObjectName,expire,timeUnit);
    }
    public String getPresignedPutUploadObjectUrl(String uploadObjectName){
        return this.getPresignedPutUploadObjectUrl(uploadObjectName,uploadSignatureExpire,TimeUnit.SECONDS);
    }

    public Map<String, String> getPresignedPostFormData(String uploadObjectName, int expire, TimeUnit timeUnit){
       return MyMinioClientUtil.getPresignedPostFormData(minioClient,bucketName,uploadObjectName,expire,timeUnit);

    }
    public Map<String, String>  getPresignedPostFormData(String uploadObjectName){
        return this.getPresignedPostFormData(uploadObjectName,uploadSignatureExpire,TimeUnit.SECONDS);
    }

    public String generateObjectNameForFile(String fileName){
        return MyMinioClientUtil.generateObjectNameForFile(fileName,dateFormat);
    }

}
