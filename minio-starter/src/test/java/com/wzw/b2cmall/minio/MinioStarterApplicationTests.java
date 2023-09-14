package com.wzw.b2cmall.minio;

import com.wzw.b2cmall.minio.utils.MinioBucketTemplate;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MinioStarterApplicationTests {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioBucketTemplate defaultMinioBucketTemplate;

    @Test
    void contextLoads() {
        System.out.println(minioClient);
        System.out.println(defaultMinioBucketTemplate);
        //String presignedDownloadObjectUrl = defaultMinioBucketTemplate.getPresignedDownloadObjectUrl("test/deskmv.mp4");
        //System.out.println(presignedDownloadObjectUrl);
    }

}
