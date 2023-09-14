package com.wzw.b2cmall.resource;


import com.wzw.b2cmall.common.utils.R;
import com.wzw.b2cmall.minio.utils.MinioBucketTemplate;
import com.wzw.b2cmall.resource.controller.ProductMinioController;
import io.minio.MinioClient;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@SpringBootTest
public class MinioPostUploadTest {
    @Autowired
    private ProductMinioController productMinioController;

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioBucketTemplate productIcnoMinioBucketTemplate;

    @Test
    public void minioPostUploadTest() throws IOException {

        Map<String,String> uploadFormData = productIcnoMinioBucketTemplate.getPresignedPostFormData("test1");
        // Upload an image using POST object with form-data.
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
        multipartBuilder.setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : uploadFormData.entrySet()) {
            multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }

        multipartBuilder.addFormDataPart(
                "file", "my-objectname", RequestBody.create(new File("D:\\XUANXIE\\Pictures\\SnipasteScreenShot\\Snipaste_2023-09-09_19-45-09.png"), null));


        Request request =
                new Request.Builder()
                        .url("http://10.0.0.208:9000/b2cmall.product.category") //一定要带上bucket地址
                        .post(multipartBuilder.build())
                        .build();

        OkHttpClient httpClient = new OkHttpClient().newBuilder().build();
        Response response = httpClient.newCall(request).execute();
        System.out.println(response);

        if (response.isSuccessful()) {
            System.out.println("Pictures/avatar.png is uploaded successfully using POST object");
        } else {
            System.out.println("Failed to upload Pictures/avatar.png");
        }
    }
}
