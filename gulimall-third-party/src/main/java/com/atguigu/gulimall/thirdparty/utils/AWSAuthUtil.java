package com.atguigu.gulimall.thirdparty.utils;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.SigningAlgorithm;
import com.atguigu.gulimall.thirdparty.vo.Config;
import com.atguigu.gulimall.thirdparty.vo.PresignedProperties;
import com.atguigu.gulimall.thirdparty.vo.S3PresignedResponse;
import org.apache.commons.codec.binary.Hex;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AWSAuthUtil extends AWS4Signer {
    private String serviceName;
    private AWSCredentials credentials;
    private String region;
    private Config config;

    public AWSAuthUtil(AWSCredentials credentials, String region, String serviceName, Config config) {
        this.credentials = credentials;
        this.region = region;
        this.serviceName = serviceName;
        this.config = config;
    }

    public String getSignature(String policy) {
        try {
            return Hex.encodeHexString(hmacSha256(newSigningKey(credentials, dateString(), region, serviceName), policy));
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    private byte[] hmacSha256(byte[] key, String data) throws Exception {
        Mac mac = Mac.getInstance(SigningAlgorithm.HmacSHA256.name());
        mac.init(new SecretKeySpec(key, SigningAlgorithm.HmacSHA256.name()));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }


    public S3PresignedResponse s3Credentails() {
        S3PresignedResponse s3PresignedResponse = new S3PresignedResponse();
        s3PresignedResponse.setEndPointUrl("https://" + config.getBucket() + ".s3."+region+".amazonaws.com");
        s3PresignedResponse.setPresignedProperties(s3Params());
        return s3PresignedResponse;
    }

    public PresignedProperties s3Params() {
        PresignedProperties presignedProperties = new PresignedProperties();
        presignedProperties.setAcl("public-read");
        presignedProperties.setKey(config.getS3Key());
        presignedProperties.setPolicy(s3UploadPolicy());
        presignedProperties.setContentType(config.getContentType());
        presignedProperties.setXAMZAlgorithm("AWS4-HMAC-SHA256");
        presignedProperties.setXAMZCredential(amzCredential());
        presignedProperties.setXAMZDate(dateString() + "T000000Z");
        presignedProperties.setXAMZSignature(getSignature(presignedProperties.getPolicy()));
        presignedProperties.setSuccessActionStatus("201");
        return presignedProperties;
    }

    public String s3UploadPolicy() {
        String policy = getPolicyStr();
        Instant t1 = Instant.now();
        Instant t2 = t1.plus(5, ChronoUnit.MINUTES);
        policy = policy.replaceAll("EXPIRATION_TIME",t2.toString());
        policy = policy.replaceAll("AWS_S3_BUCKET",config.getBucket());
        policy = policy.replaceAll("AWS_S3_KEY",config.getS3Key());
        policy = policy.replaceAll("CONTENT_LENGTH_MAX",131072000 +"");
        policy = policy.replaceAll("X_AWS_CREDENTIAL",amzCredential());
        policy = policy.replaceAll("AWS_DATE",dateString() + "T000000Z");

        String policyBase64 = null;
        try {
            policyBase64 = Base64.getEncoder().encodeToString(policy.getBytes("UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return policyBase64;
    }
    private String getPolicyStr() {
        //return "{\"expiration\":\"EXPIRATION_TIME\",\"conditions\":[{\"bucket\":\"AWS_S3_BUCKET\"},{\"key\":\"AWS_S3_KEY\"},{\"acl\":\"public-read\"},{\"success_action_status\":\"201\"},[\"starts-with\",\"$Content-Type\",\"\"],[\"content-length-range\",0,CONTENT_LENGTH_MAX],{\"x-amz-algorithm\":\"AWS4-HMAC-SHA256\"},{\"x-amz-credential\":\"X_AWS_CREDENTIAL\"},{\"x-amz-date\":\"AWS_DATE\"}]}";

        try {
            return StreamUtils.copyToString((new ClassPathResource("policy")).getInputStream(), UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String dateString() {
        String text = Instant.now().toString();
        return text.substring(0, 4) + text.substring(5, 7) + text.substring(8, 10);
    }

    public String amzCredential() {
        List<String> credentialList = Arrays.asList(credentials.getAWSAccessKeyId(), dateString(), region, "s3/aws4_request");
        return credentialList.stream().collect(Collectors.joining("/"));
    }


}
