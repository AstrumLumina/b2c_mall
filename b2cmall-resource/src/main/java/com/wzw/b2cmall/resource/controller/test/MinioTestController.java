package com.wzw.b2cmall.resource.controller.test;

import com.wzw.b2cmall.common.utils.R;
import com.wzw.b2cmall.minio.utils.MinioBucketTemplate;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/minio")
public class MinioTestController {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioBucketTemplate productCategoryIconMinioBucketTemplate;

    @GetMapping("/presign/downloadfile")
    public R<String> testMinioPresignGetUrl(){
       return R.ok().setData(productCategoryIconMinioBucketTemplate.getPresignedDownloadObjectUrl("test/deskmv.mp4"));
    }

    @GetMapping("/presign/postupload")
    public R<String> testMinioPresignPostUrl(){
        return R.ok().setData(productCategoryIconMinioBucketTemplate.getPresignedPostFormData("test/putfile"));
    }

}
