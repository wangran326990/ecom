package com.atguigu.gulimall.thirdparty.controller;


import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.thirdparty.utils.AWSAuthUtil;
import com.atguigu.gulimall.thirdparty.vo.Config;
import com.atguigu.gulimall.thirdparty.vo.S3PresignedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.net.URL;
import java.util.UUID;


@RestController
public class OssController {

    @Autowired
    AmazonS3 s3Client;
    @Value("${cloud.s3.bucket}")
    String bucket;
    @Value("${cloud.s3.apiKey}")
    private String apiKey;
    @Value("${cloud.s3.apiSecret}")
    private String s3Secret;
    @Value("${cloud.aws.region.static}")
    private String region;
    @RequestMapping("/oss/policy/{fileName}/{contentType}")
    public R policy(@PathVariable("fileName") String fileName, @PathVariable("contentType") String contentType) {

        String s3Key = UUID.randomUUID().toString()+"_"+fileName;
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(apiKey, s3Secret);
        Config config = new Config();
        config.setContentType(contentType);
        config.setBucket(bucket);
        config.setS3Key(s3Key);
        AWSAuthUtil awsAuthUtil = new AWSAuthUtil(basicAWSCredentials, region, "s3",config);
        S3PresignedResponse s3PresignedResponse = awsAuthUtil.s3Credentails();
        return R.ok();
    }
    @RequestMapping("/s3_credentials")
    public S3PresignedResponse test() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(apiKey, s3Secret);
        Config config = new Config();
        config.setContentType("image/jpeg");
        config.setBucket(bucket);
        config.setS3Key("744b2bb80388ff9e2119b9cb11c16204.jpg");
        AWSAuthUtil awsAuthUtil = new AWSAuthUtil(basicAWSCredentials, region, "s3",config);
        return awsAuthUtil.s3Credentails();
    }
}
