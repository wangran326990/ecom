package com.atguigu.gulimall.thirdparty;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.BasicAWSCredentials;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.atguigu.gulimall.thirdparty.utils.AWSAuthUtil;
import com.atguigu.gulimall.thirdparty.vo.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@SpringBootTest
class GulimallThirdPartyApplicationTests {


    @Autowired
    AmazonS3 s3Client;
    @Autowired
    ObjectMapper objectMapper;


    private String s3Key = "123";
    private String s3Secret = "123/xMNFdZwvTlRJYKH4k ";
    private String bucket = "aws-123";
    private String region = "ca-123-1";
    @Test
    void contextLoads() {


    }


    @Test
    public void testUpload() throws Exception {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(s3Key, s3Secret);
        Config config = new Config();
        config.setContentType("image/jpeg");
        config.setBucket(bucket);
        config.setS3Key("fileName.txt");
        AWSAuthUtil awsAuthUtil = new AWSAuthUtil(basicAWSCredentials, region, "s3",config);
        System.out.println(awsAuthUtil.getSignature(null));
    }
    @Test
    public void testUsePresignedUrlToUpload() throws JsonProcessingException {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(s3Key, s3Secret);
        Config config = new Config();
        config.setContentType("image/jpeg");
        config.setBucket(bucket);
        config.setS3Key("fileName.txt");
        AWSAuthUtil awsAuthUtil = new AWSAuthUtil(basicAWSCredentials, region, "s3",config);

        String jsonStr = objectMapper.writeValueAsString(awsAuthUtil.s3Credentails());

        System.out.println(awsAuthUtil.s3UploadPolicy());
    }

    @Test void PresignedUrlUpload() throws IOException {

        String bucketName = "aws-product";
        String objectKey = "test-post";

        try {

            // Set the pre-signed URL to expire after one hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the pre-signed URL.
            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
                    .withMethod(HttpMethod.POST)
                    .withExpiration(expiration);
            Map requestParams = generatePresignedUrlRequest.getRequestParameters();
            System.out.println(requestParams);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            System.out.println(url.toString());
            // Create the connection and use it to upload the new object using the pre-signed URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write("This text uploaded as an object via presigned URL.");
            out.close();

            // Check the HTTP response code. To complete the upload and make the object available,
            // you must interact with the connection object in some way.
            connection.getResponseCode();
            System.out.println("HTTP response code: " + connection.getResponseCode());

            // Check to make sure that the object was uploaded successfully.
            S3Object object = s3Client.getObject(bucketName, objectKey);
            System.out.println("Object " + object.getKey() + " created in bucket " + object.getBucketName());
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
}
