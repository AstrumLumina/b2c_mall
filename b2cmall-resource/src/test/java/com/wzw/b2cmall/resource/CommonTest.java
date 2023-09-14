package com.wzw.b2cmall.resource;

import io.minio.*;
import io.minio.credentials.Credentials;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.ResponseDate;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;



public class CommonTest {

    private static final String minioEndpoint="http://10.0.0.208:9000";
    private static final String minioAccesKey="minio";
    private static final String minioSecrectKey="password";
    private static final String minioBucketName="b2cmall.product.category";
    private static final String gatewayHost="http://10.0.0.1:8086";

    private static int minioUploadDuration=4;

     // Create a minioClient with the MinIO server playground, its access key and secret key.
     private static  final MinioClient minioClient =
            MinioClient.builder()
                    .endpoint(minioEndpoint)
                    .credentials(minioAccesKey, minioSecrectKey)
                    .build();

    public static void main(String[] args) {

    }


    @Test
    public void testMinioUpload(){
        try {
            // Make 'asiatrip' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioBucketName).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioBucketName).build());
            } else {
                System.out.println("Bucket 'b2cmall.product.category' already exists.");
            }

            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            // 'asiatrip'.
            ObjectWriteResponse objectWriteResponse = minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(minioBucketName)
                            .object("test/deskmv.mp4")
                            .filename("D:\\forPhoto\\picture_9.mp4")
                            .build());

            System.out.println(
                    "'D:\\forPhoto\\picture_9.mp4' is successfully uploaded as "
                            + "object 'est/deskmv.mp4' to bucket 'b2cmall.product.category'.");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public  void testPresignedDownloadObject(){
        try {
            // Get presigned URL string to download 'my-objectname' in 'my-bucketname'
            // with an expiration of 2 hours.
            //
            // Additionally also add 'response-content-type' to dynamically set content-type
            // for the server response.
            Map<String, String> reqParams = new HashMap<String, String>();
            reqParams.put("response-content-type", "video/mp4");

            String url =
                    minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket(minioBucketName)
                                    .object("test/deskmv.mp4")
                                    .expiry(minioUploadDuration, TimeUnit.MINUTES)
                                    .extraQueryParams(reqParams)
                                    .build());
            System.out.println(url);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testPresignedPutUploadObject(){
        try {
            Map<String, String> reqParams = new HashMap<String, String>();
            //测试上传图片,是二进制数据,具体那种图片格式不能完全确定,所以这里干脆不指定类型
            //reqParams.put("response-content-type", "application/json");

            // Get presigned URL string to upload 'my-objectname' in 'my-bucketname'
            String url1 =
                    minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.PUT)
                                    .bucket(minioBucketName)
                                    .object("test/uploadtestfile1")
                                    .expiry(1, TimeUnit.HOURS)
                                    .extraQueryParams(reqParams)
                                    .build());
            System.out.println("url1 :");
            System.out.println(url1);


            // Get presigned URL string to lookup metadata for 'my-objectname' in 'my-bucketname'
            // Additionally also add 'response-content-type' to dynamically set content-type
            // for the server metadata response.



            //加入回调请求头
            Map<String, String> headParams2 = new HashMap<String, String>();
            //这种方法没有用,无效果
            headParams2.put("x-amz-meta-callback-url", gatewayHost+"/b2cmall/product/test/config/getinfo");
            String url2 =
                    minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.PUT)
                                    .bucket(minioBucketName)
                                    .object("test/uploadtestfile2")
                                    .expiry(minioUploadDuration, TimeUnit.DAYS)
                                    .extraHeaders(headParams2)
                                    .build());
            System.out.println("url2 : ");
            System.out.println(url2);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public  String extractURI(String urlString) {
        int hostEndIndex=urlString.indexOf("://");
        if (hostEndIndex==-1){
            hostEndIndex=0;
        }else {
            hostEndIndex+=3;
        }
        int startIndex = urlString.indexOf('/', hostEndIndex);
        if (startIndex == -1 ) {
            return "";
        }
        return urlString.substring(startIndex, urlString.length());
    }
    @Test
    public  void testUri(){
        String url = "https://www.example.com/path/to/resource?key1=value1&key2=value2#fragment";
        String extractedURI = extractURI(url);
        System.out.println(extractedURI);

        String url2 = "10.0.0.1/path/to/resource?key1=value1&key2=value2#fragment";
        String extractedURI2 = extractURI(url);
        System.out.println(extractedURI2);
    }


    @Test
    public void testPostUploadFile(){
        try {
            PostPolicy policy = new PostPolicy(minioBucketName, ZonedDateTime.now().plusDays(7));
            policy.addEqualsCondition("key", "my-objectname");
            policy.addStartsWithCondition("Content-Type", "image/");

            Map<String, String> formData = minioClient.getPresignedPostFormData(policy);

            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
            multipartBuilder.setType(MultipartBody.FORM);
            for (Map.Entry<String, String> entry : formData.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
                System.out.println();
                multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            multipartBuilder.addFormDataPart("key", "my-objectname");

            multipartBuilder.addFormDataPart(
                    "file", "my-objectname", RequestBody.create(new File("D:\\XUANXIE\\Pictures\\SnipasteScreenShot\\Snipaste_2023-08-21_11-00-35.png"), null));

            Request request =
                    new Request.Builder()
                            .url(minioEndpoint)
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
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        }

    }

}
