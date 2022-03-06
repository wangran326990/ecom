package com.atguigu.gulimall.thirdparty;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;

@SpringBootTest
class GulimallThirdPartyApplicationTests {


    @Autowired
    AmazonS3 s3Client;
    @Test
    void contextLoads() {


    }


    @Test
    public void testUpload() throws FileNotFoundException {
        String bucket = "aws-product";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, "upload-spring",new File("C:\\projects\\pic\\test.txt"));
        s3Client.putObject(putObjectRequest);
    }
}
