package com.wzw.b2cmall.resource.controller;


import com.wzw.b2cmall.common.utils.R;
import com.wzw.b2cmall.minio.utils.MyMinioClientUtil;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/minio")
public class MinioController {
    @Autowired
    private MinioClient minioClient;

    @GetMapping("/presigndownload/{bucketName}/{objectName}/{expire}")
    public R<String> getDownloadUrl(@PathVariable String bucketName, @PathVariable String objectName, @PathVariable(required = false) Integer expire){
        if (expire==null){
            return R.ok().setData(MyMinioClientUtil.getPresignedDownloadObjectUrl(minioClient,bucketName, objectName));
        }else {
            return R.ok().setData(MyMinioClientUtil.getPresignedDownloadObjectUrl(minioClient,bucketName, objectName,expire,TimeUnit.SECONDS));
        }
    }
    //这种上传方式上传的商品分类icon 未知,具有随机性,这样导致需要保证数据一致性(存在旧数据删除以及更新icon后数据库为及时更新甚至更新丢失的情况),很麻烦,采用下面每个商品使用固定的名字,避免此种情况
    @GetMapping("/presignputupload/{bucketName}/{objectName}/{expire}")
    public R<String> getPutUploadUrl(@PathVariable String bucketName, @PathVariable String objectName, @PathVariable(required = false) Integer expire){
      if (expire==null){
          return R.ok().setData(MyMinioClientUtil.getPresignedPutUploadObjectUrl(minioClient,bucketName, objectName));
      }else {
          return R.ok().setData(MyMinioClientUtil.getPresignedPutUploadObjectUrl(minioClient,bucketName, objectName,expire,TimeUnit.SECONDS));
      }
    }
    @GetMapping("/presignpostupload/{bucketName}/{objectName}/{expire}")
    public R<String> getPostUploadUrl(@PathVariable String bucketName,@PathVariable String objectName, @PathVariable(required = false) Integer expire){
        if (expire==null){
            return R.ok().setData(MyMinioClientUtil.getPresignedPostFormData(minioClient,bucketName, objectName));
        }else {
            return R.ok().setData(MyMinioClientUtil.getPresignedPostFormData(minioClient,bucketName, objectName,expire, TimeUnit.SECONDS));

        }
    }

}
